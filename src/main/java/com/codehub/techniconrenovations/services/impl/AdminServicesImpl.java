package com.codehub.techniconrenovations.services.impl;

import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import com.codehub.techniconrenovations.services.AdminServices;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;

public class AdminServicesImpl implements AdminServices {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;
    @Inject
    private PropertyRepository propertyRepository;
    @Inject
    private PropertyOwnerRepository propertyOwnerRepository;
    @Inject
    private PropertyRepairRepository propertyRepairRepository;

    @Inject
    public AdminServicesImpl(PropertyRepository propertyRepository, PropertyOwnerRepository propertyOwnerRepository, PropertyRepairRepository propertyRepairRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.propertyRepairRepository = propertyRepairRepository;
    }

    @Override
    public List<PropertyRepair> getPendingRepairs() {
        return entityManager.createQuery("SELECT r FROM PropertyRepair r WHERE r.status = :status")
                .setParameter("status", Status.PENDING)
                .getResultList();
    }

    @Override
    public List<PropertyRepair> getAllRepairs() {
        return entityManager.createQuery("SELECT r FROM PropertyRepair r")
                .getResultList();
    }

    @Override
    public List<Property> getProperties() {
        return entityManager.createQuery("SELECT p FROM Property p")
                .getResultList();
    }

    @Override
    public List<PropertyOwner> getOwners() {
        return entityManager.createQuery("SELECT o FROM PropertyOwner o")
                .getResultList();
    }

    @Override
    public boolean proposeCost(double cost, int repairId) {
        PropertyRepair p = propertyRepairRepository.searchById(repairId);
        if (p == null) {
            return false;
        }
        propertyRepairRepository.updateProposedCost(cost, repairId);
        return true;
    }

    @Override
    public boolean proposeStartEndDates(Date startDate, Date endDate, int repairId) {
        PropertyRepair p = propertyRepairRepository.searchById(repairId);
        if (p == null) {
            return false;
        }
        propertyRepairRepository.updateProposedStartDate(startDate, repairId);
        propertyRepairRepository.updateProposedEndDate(endDate, repairId);
        return true;
    }

    @Override
    public void permanentlyDeleteProperties() {
        permanentlyDeletePropertyRepairs();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE Property WHERE isDeleted = :isDeleted")
                .setParameter("isDeleted", true);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * Removes all properties for a specific owner from the database.
     *
     * @param vatNumber
     */
    private void permanentlyDeleteRepairsList(String propertyId) {
        Property property = new Property(propertyId);
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE PropertyRepair WHERE property = :property")
                .setParameter("property", property);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public void permanentlyDeletePropertyRepairs() {
        List<Property> properties = entityManager.createQuery("SELECT o FROM Property o WHERE isDeleted = :isDeleted")
                .setParameter("isDeleted", true).getResultList();
        properties.forEach(p -> permanentlyDeleteRepairsList(p.getPropertyId()));
    }

    /**
     * Removes all repairs for a specific owner from the database.
     *
     * @param vatNumber
     */
    public void permanentlyDeleteRepairs(int vatNumber) {
        PropertyOwner propertyOwner = new PropertyOwner(vatNumber);
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE PropertyRepair WHERE propertyOwner = :propertyOwner")
                .setParameter("propertyOwner", propertyOwner);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * Removes all properties for a specific owner from the database.
     *
     * @param vatNumber
     */
    public void permanentlyDeleteProperties(int vatNumber) {
        PropertyOwner propertyOwner = new PropertyOwner(vatNumber);
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE Property WHERE propertyOwner = :propertyOwner")
                .setParameter("propertyOwner", propertyOwner);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    public void permanentlyDeletePropertyOwner() {
        List<PropertyOwner> propertyOwner = entityManager.createQuery("SELECT o FROM PropertyOwner o WHERE isDeleted = :isDeleted")
                .setParameter("isDeleted", true)
                .getResultList();
        propertyOwner.forEach(po -> {
            permanentlyDeleteRepairs(po.getVatNumber());
            permanentlyDeleteProperties(po.getVatNumber());
            propertyOwnerRepository.permanentlyDelete(po.getVatNumber());
        });
    }

    @Override
    public void permanentlyDeleteRepairs() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("DELETE PropertyRepair WHERE isDeleted = :isDeleted")
                .setParameter("isDeleted", true);
        query.executeUpdate();
        entityManager.getTransaction().commit();
    }
}

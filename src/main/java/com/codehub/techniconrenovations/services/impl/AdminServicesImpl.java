package com.codehub.techniconrenovations.services.impl;

import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.services.AdminServices;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminServicesImpl implements AdminServices {

    private static final Logger logger = LoggerFactory.getLogger(AdminServicesImpl.class);

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;
    
    @Inject
    private PropertyRepairRepository propertyRepairRepository;

    @Override
    public List<RepairDto> getPendingRepairs() {
        try {
            List<RepairDto> repairDtoList = entityManager.createQuery("SELECT r FROM PropertyRepair r WHERE r.status = :status")
                    .setParameter("status", Status.PENDING)
                    .getResultList();
            return repairDtoList;
        } catch (Exception e) {
            logger.error("Error while retrieving pending repairs: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<PropertyRepair> getAllRepairs() {
        try {
            return entityManager.createQuery("SELECT r FROM PropertyRepair r")
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error while retrieving all repairs: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Property> getProperties() {
        try {
            return entityManager.createQuery("SELECT p FROM Property p")
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error while retrieving properties: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<PropertyOwner> getOwners() {
        try {
            return entityManager.createQuery("SELECT o FROM PropertyOwner o")
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error while retrieving owners: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public boolean proposeCost(double cost, int repairId) {
        try {
            PropertyRepair p = propertyRepairRepository.searchById(repairId);
            if (p == null) {
                logger.error("Error repair is null: ");
                return false;
            }
            propertyRepairRepository.updateProposedCost(cost, repairId);
            logger.debug("proposeCost was updated succefully");
            return true;
        } catch (Exception e) {
            logger.error("Error while proposing cost for repair: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean proposeStartEndDates(Date startDate, Date endDate, int repairId) {
        try {
            PropertyRepair p = propertyRepairRepository.searchById(repairId);
            if (p == null) {
                logger.error("Error repair is null: ");
                return false;
            }
            propertyRepairRepository.updateProposedStartDate(startDate, repairId);
            propertyRepairRepository.updateProposedEndDate(endDate, repairId);
            logger.debug("proposeStartEndDates was updated succefully");
            return true;
        } catch (Exception e) {
            logger.error("Error while proposing start and end dates for repair: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean permanentlyDeleteProperties() {
        try {
            permanentlyDeletePropertyRepairs();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE Property WHERE isDeleted = :isDeleted")
                    .setParameter("isDeleted", true);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            logger.debug("permanentlyDeleteProperties was succefully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting properties: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Removes all properties for a specific owner from the database.
     *
     * @param vatNumber
     */
    private void permanentlyDeleteRepairsList(String propertyId) {
        try {
            Property property = new Property(propertyId);
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE PropertyRepair WHERE property = :property")
                    .setParameter("property", property);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            logger.debug("permanentlyDeleteRepairsList was succefully");
        } catch (Exception e) {
            logger.error("Error while deleting repairs list for property: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean permanentlyDeletePropertyRepairs() {
        try {
            List<Property> properties = entityManager.createQuery("SELECT o FROM Property o WHERE isDeleted = :isDeleted")
                    .setParameter("isDeleted", true).getResultList();
            properties.forEach(p -> permanentlyDeleteRepairsList(p.getPropertyId()));
            logger.debug("permanentlyDeletePropertyRepairs was succefully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting property repairs: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * Removes all repairs for a specific owner from the database.
     *
     * @param vatNumber
     */
    public void permanentlyDeleteRepairs(int vatNumber) {
        try {
            PropertyOwner propertyOwner = new PropertyOwner(vatNumber);
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE PropertyRepair WHERE propertyOwner = :propertyOwner")
                    .setParameter("propertyOwner", propertyOwner);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            logger.debug("permanentlyDeleteRepairs was succefully");
        } catch (Exception e) {
            logger.error("Error while deleting repairs for owner: " + e.getMessage(), e);
        }
    }

    /**
     * Removes all properties for a specific owner from the database.
     *
     * @param vatNumber
     */
    public void permanentlyDeleteProperties(int vatNumber) {
        try {
            PropertyOwner propertyOwner = new PropertyOwner(vatNumber);
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE Property WHERE propertyOwner = :propertyOwner")
                    .setParameter("propertyOwner", propertyOwner);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            logger.debug("permanentlyDeleteProperties was succefully");
        } catch (Exception e) {
            logger.error("Error while deleting properties for owner: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean permanentlyDeletePropertyOwner() {
        try {
            List<PropertyOwner> propertyOwner = entityManager.createQuery("SELECT o FROM PropertyOwner o WHERE isDeleted = :isDeleted")
                    .setParameter("isDeleted", true).getResultList();
            propertyOwner.forEach(o -> {
                permanentlyDeleteProperties(o.getVatNumber());
                permanentlyDeleteRepairs(o.getVatNumber());
            });
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE PropertyOwner WHERE isDeleted = :isDeleted").setParameter("isDeleted", true);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            logger.debug("permanentlyDeletePropertyOwner was succefully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting property owner: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean permanentlyDeleteRepairs() {
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE PropertyRepair WHERE isDeleted = :isDeleted")
                    .setParameter("isDeleted", true);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            logger.debug("permanentlyDeleteRepairs was succefully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting property repairs: " + e.getMessage(), e);
            return false;
        }
    }
}

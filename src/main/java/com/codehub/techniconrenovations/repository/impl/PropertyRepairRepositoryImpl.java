package com.codehub.techniconrenovations.repository.impl;

import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;

public class PropertyRepairRepositoryImpl implements PropertyRepairRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean createPropertyRepair(PropertyRepair propertyRepair) {
        entityManager.getTransaction().begin();
        entityManager.persist(propertyRepair);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public void updateOwnerId(int vatNumber, int repairId) {
        PropertyRepair p = entityManager.find(PropertyRepair.class, repairId);
        p.setPropertyOwner(new PropertyOwner(vatNumber));
        createPropertyRepair(p);
    }

    @Override
    @Transactional
    public void updatePropertyId(String propertyId, int repairId) {
        PropertyRepair p = entityManager.find(PropertyRepair.class, repairId);
        p.setProperty(new Property(propertyId));
        createPropertyRepair(p);
    }

    @Override
    @Transactional
    public void updateRepairType(RepairType repairType, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set repairType = : repairType WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("repairType", repairType)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateShortDescription(String shortDescription,int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set shortDescription = : shortDescription WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("shortDescription", shortDescription)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateDescription(String desciption, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set description = : desciption WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("desciption", desciption)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateProposedStartDate(Date proposedStartDate, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set proposedStartDate= :proposedStartDate WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("proposedStartDate", proposedStartDate)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateProposedEndDate(Date proposedEndDate, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set proposedEndDate= :proposedEndDate WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("proposedEndDate", proposedEndDate)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateProposedCost(Double proposedCost, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set proposedCost = : proposedCost WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("proposedCost", proposedCost)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateAccepted(boolean accepted, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set accepted = : accepted WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("accepted", accepted)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateStatus(Status status, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set status = : status WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("status", status)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateActualStartDate(int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set actualStartDate =  proposedStartDate WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateActualEndDate(int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set actualEndDate =  proposedEndDate WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public void updateIsDeleted(boolean isDeleted, int repairId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyRepair set isDeleted = : isDeleted WHERE repairId =: id";
        entityManager.createQuery(updateQuery).setParameter("isDeleted", isDeleted)
                .setParameter("id", repairId).executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByDate(Date date) {
        return entityManager.createQuery("SELECT o FROM PropertyRepair o WHERE o.actualStartDate = :date OR o.actualEndDate= :date OR o.date= :date", PropertyRepair.class)
                .setParameter("date", date).getResultList();
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByRangeOfDates(Date rangeStartDate, Date rangeEndDate) {
        return entityManager.createQuery("SELECT o FROM PropertyRepair o WHERE actualStartDate>=:rangeStartDate AND actualEndDate<=:rangeEndDate")
                .setParameter("rangeStartDate", rangeStartDate)
                .setParameter("rangeEndDate", rangeEndDate)
                .getResultList();
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByOwnerVat(int vatNumber) {
        return entityManager.createQuery("SELECT o FROM PropertyRepair o INNER JOIN PropertyOwner p ON o.propertyOwner = p.vatNumber WHERE o.isDeleted = false AND p.vatNumber = " + vatNumber)
                .getResultList();
    }

    @Override
    @Transactional
    public PropertyRepair searchById(int repairId) {
        return entityManager.find(PropertyRepair.class, repairId);
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByPropertyId(String propertyId) {
        return entityManager.createQuery("SELECT p FROM PropertyRepair p INNER JOIN Property o ON p.property = o.propertyId WHERE o.isDeleted = FAlSE AND  o.propertyId =:propertyId")
                .setParameter("propertyId", propertyId).getResultList();
    }

    @Override
    @Transactional
    public boolean safelyDelete(int repairId) {
        updateIsDeleted(true, repairId);
        return true;
    }

    @Override
    @Transactional
    public boolean permanentlyDelete(int repairId) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE PropertyRepair WHERE repairId = :repairId")
                .setParameter("repairId", repairId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }
}

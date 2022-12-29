package com.codehub.techniconrenovations.repository.impl;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

public class PropertyRepositoryImpl implements PropertyRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean createProperty(Property property) {
        if(searchPropertyId(property.getPropertyId())!= null) return false;//causing crush
        entityManager.getTransaction().begin();
        entityManager.persist(property);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public Property searchPropertyId(String propertyId) {
        return entityManager.find(Property.class, propertyId);
    }

    @Override
    @Transactional
    public List<Property> searchVat(int ownerVat) {
        return entityManager.createQuery("SELECT p FROM Property p INNER JOIN PropertyOwner o ON  p.propertyOwner = o.vatNumber WHERE o.isDeleted = FALSE AND o.vatNumber =" + ownerVat)
                .getResultList();
    }

    @Override
    @Transactional
    public boolean updatePropertyId(String newPropertyId, String propertyId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE Property set propertyId= :propertyId WHERE propertyId =: id";
        entityManager.createQuery(updateQuery).setParameter("propertyId", propertyId)
                .setParameter("id", propertyId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updatePropertyAddress(String address, String propertyId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE Property set propertyAddress= :address WHERE propertyId =: id";
        entityManager.createQuery(updateQuery).setParameter("address", address)
                .setParameter("id", propertyId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updateYearOfConstruction(int yearOfConstruction, String propertyId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE Property set yearOfConstruction= :yearOfConstruction WHERE propertyId =: id";
        entityManager.createQuery(updateQuery).setParameter("yearOfConstruction", yearOfConstruction)
                .setParameter("id", propertyId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updatePropertyType(PropertyType propertyType, String propertyId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE Property set propertyType= :propertyType WHERE propertyId =: id";
        entityManager.createQuery(updateQuery).setParameter("propertyType", propertyType)
                .setParameter("id", propertyId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updateOwnerVat(int ownerVat, String propertyId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE Property set ownerVat= :ownerVat WHERE propertyId =: id";
        entityManager.createQuery(updateQuery).setParameter("ownerVat", ownerVat)
                .setParameter("id", propertyId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean safelyDelete(String propertyId) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE Property set isDeleted = :isDeleted WHERE propertyId = :propertyId";
        entityManager.createQuery(updateQuery)
                .setParameter("isDeleted", true)
                .setParameter("propertyId", propertyId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean permanentlyDelete(String propertyId) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE PropertyOwner WHERE propertyId = :propertyId")
                .setParameter("propertyId", propertyId).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }
}

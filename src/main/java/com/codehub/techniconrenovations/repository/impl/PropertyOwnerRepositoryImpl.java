package com.codehub.techniconrenovations.repository.impl;

import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

public class PropertyOwnerRepositoryImpl implements PropertyOwnerRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean createPropertyOwner(PropertyOwner propertyOwner) throws jakarta.persistence.RollbackException{
        if (searchVat(propertyOwner.getVatNumber()) != null) {
            return false;
        }
        entityManager.getTransaction().begin();
        entityManager.persist(propertyOwner);
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public PropertyOwner searchVat(int vatNumber) {
        return entityManager.find(PropertyOwner.class, vatNumber);
    }

    @Override
    @Transactional
    public PropertyOwner searchEmail(String email) {
        return entityManager.createQuery("SELECT o FROM PropertyOwner o WHERE o.email = :ownerEmail", PropertyOwner.class)
                .setParameter("ownerEmail", email).getSingleResult();
    }

    @Override
    @Transactional
    public PropertyOwner searchByUsernameAndPassword(String username, String password) {
        try {
            PropertyOwner propertyOwner= entityManager.createQuery("SELECT o FROM PropertyOwner o WHERE o.username = :username AND o.password = :password AND isDeleted = :isDeleted", PropertyOwner.class)
                    .setParameter("username", username).setParameter("password", password).setParameter("isDeleted", false).getSingleResult();
            if(propertyOwner.getUsername().equals(username)&& propertyOwner.getPassword().equals(password)){
                return propertyOwner;
            }
            return null;
        } catch (jakarta.persistence.NoResultException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public boolean updateAddress(String address, int vatNumber) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyOwner set address = :address WHERE vatNumber = :vatNumber";
        entityManager.createQuery(updateQuery)
                .setParameter("address", address)
                .setParameter("vatNumber", vatNumber).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updateEmail(String email, int vatNumber) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyOwner set email = :email WHERE vatNumber = :vatNumber";
        entityManager.createQuery(updateQuery)
                .setParameter("email", email)
                .setParameter("vatNumber", vatNumber).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updatePassword(String password, int vatNumber) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyOwner set password = :password WHERE vatNumber = :vatNumber";
        entityManager.createQuery(updateQuery)
                .setParameter("password", password)
                .setParameter("vatNumber", vatNumber).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean updateUsername(String username, int vatNumber) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyOwner set username = :username WHERE vatNumber = :vatNumber";
        entityManager.createQuery(updateQuery)
                .setParameter("username", username)
                .setParameter("vatNumber", vatNumber).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean safelyDelete(int vatNumber) {
        entityManager.getTransaction().begin();
        String updateQuery = "UPDATE PropertyOwner set isDeleted = :isDeleted WHERE vatNumber = :vatNumber";
        entityManager.createQuery(updateQuery)
                .setParameter("isDeleted", true)
                .setParameter("vatNumber", vatNumber).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }

    @Override
    @Transactional
    public boolean permanentlyDelete(int vatNumber) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE PropertyOwner WHERE vatNumber = :vatNumber")
                .setParameter("vatNumber", vatNumber).executeUpdate();
        entityManager.getTransaction().commit();
        return true;
    }
}

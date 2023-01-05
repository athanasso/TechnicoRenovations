package com.codehub.techniconrenovations.repository.impl;

import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyOwnerRepositoryImpl implements PropertyOwnerRepository {

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(PropertyOwnerRepositoryImpl.class);

    @Override
    @Transactional
    public boolean createPropertyOwner(PropertyOwner propertyOwner) throws jakarta.persistence.RollbackException {
        try {
            if (searchVat(propertyOwner.getVatNumber()) != null) {
                logger.error("something went wrong");
                return false;
            }
            entityManager.persist(propertyOwner);
            logger.debug("createPropertyOwner was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error creating property owner: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public PropertyOwner searchVat(int vatNumber) {
        try {
            return entityManager.find(PropertyOwner.class, vatNumber);
        } catch (Exception e) {
            logger.error("Error searching for property owner by VAT number: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Transactional
    public PropertyOwner searchEmail(String email) {
        try {
            return entityManager.createQuery("SELECT o FROM PropertyOwner o WHERE o.email = :ownerEmail", PropertyOwner.class)
                    .setParameter("ownerEmail", email).getSingleResult();
        } catch (Exception e) {
            logger.error("Error searching for property owner by email: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Transactional
    public PropertyOwner searchByUsernameAndPassword(String username, String password) {
        try {
            PropertyOwner propertyOwner = entityManager.createQuery("SELECT o FROM PropertyOwner o WHERE o.username = :username AND o.password = :password AND isDeleted = :isDeleted", PropertyOwner.class)
                    .setParameter("username", username).setParameter("password", password).setParameter("isDeleted", false).getSingleResult();
            if (propertyOwner.getUsername().equals(username) && propertyOwner.getPassword().equals(password)) {
                logger.debug("searchByUsernameAndPassword was succesful");
                return propertyOwner;
            }
            return null;
        } catch (jakarta.persistence.NoResultException e) {
            logger.error("Error searching for property owner by username and password: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    @Transactional
    public boolean updateAddress(String address, int vatNumber) {
        try {
            String updateQuery = "UPDATE PropertyOwner set address = :address WHERE vatNumber = :vatNumber";
            entityManager.createQuery(updateQuery)
                    .setParameter("address", address)
                    .setParameter("vatNumber", vatNumber).executeUpdate();
            logger.debug("updateAddress was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error updating property owner address: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateEmail(String email, int vatNumber) {
        try {
            String updateQuery = "UPDATE PropertyOwner set email = :email WHERE vatNumber = :vatNumber";
            entityManager.createQuery(updateQuery)
                    .setParameter("email", email)
                    .setParameter("vatNumber", vatNumber).executeUpdate();
            logger.debug("updateEmail was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error updating property owner email: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updatePassword(String password, int vatNumber) {
        try {
            String updateQuery = "UPDATE PropertyOwner set password = :password WHERE vatNumber = :vatNumber";
            entityManager.createQuery(updateQuery)
                    .setParameter("password", password)
                    .setParameter("vatNumber", vatNumber).executeUpdate();
            logger.debug("updatePassword was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error updating property owner password: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateUsername(String username, int vatNumber) {
        try {
            String updateQuery = "UPDATE PropertyOwner set username = :username WHERE vatNumber = :vatNumber";
            entityManager.createQuery(updateQuery)
                    .setParameter("username", username)
                    .setParameter("vatNumber", vatNumber).executeUpdate();
            logger.debug("updateUsername was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error updating property owner username: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean safelyDelete(int vatNumber) {
        try {
            String updateQuery = "UPDATE PropertyOwner set isDeleted = :isDeleted WHERE vatNumber = :vatNumber";
            entityManager.createQuery(updateQuery)
                    .setParameter("isDeleted", true)
                    .setParameter("vatNumber", vatNumber).executeUpdate();
            logger.debug("safelyDelete was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error safely deleting property owner: " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional
    public boolean permanentlyDelete(int vatNumber) {
        try {
            entityManager.createQuery("DELETE PropertyOwner WHERE vatNumber = :vatNumber")
                    .setParameter("vatNumber", vatNumber).executeUpdate();
            logger.debug("permanentlyDelete was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error permamently deleting property owner: " + e.getMessage(), e);
            return false;
        }
    }
    
    @Override
    public String checkRole(String username, String password) {
        try {
            logger.debug("checking user role");
            return entityManager.createQuery("SELECT o.typeOfUser from PropertyOwner o where username=:username and password=:password")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult()
                    .toString();
        } catch (Exception e) {
            logger.error("Error checking user's role " + e.getMessage(), e);
            return "";
        }
    }
}

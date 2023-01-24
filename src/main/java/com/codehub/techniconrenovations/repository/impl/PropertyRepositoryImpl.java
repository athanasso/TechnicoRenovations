package com.codehub.techniconrenovations.repository.impl;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyRepositoryImpl implements PropertyRepository {

    private static final Logger logger = LoggerFactory.getLogger(PropertyRepositoryImpl.class);

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public boolean createProperty(Property property) {
        try {
            if (searchPropertyId(property.getPropertyId()) != null) {
                return false;
            }
            entityManager.persist(property);
            logger.debug("createProperty was successful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while creating a property: ", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public Property searchPropertyId(String propertyId) {
        try {
            return entityManager.find(Property.class, propertyId);
        } catch (Exception e) {
            logger.error("An error occurred while searching for a property by ID: ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public List<Property> searchVat(int ownerVat) {
        try {
            return entityManager.createQuery("SELECT p FROM Property p INNER JOIN PropertyOwner o ON  p.propertyOwner = o.vatNumber WHERE o.isDeleted = FALSE AND o.vatNumber =" + ownerVat)
                    .getResultList();
        } catch (Exception e) {
            logger.error("An error occurred while searching for properties by VAT number: ", e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public boolean updatePropertyId(String newPropertyId, String propertyId) {
        try {
            String updateQuery = "UPDATE Property set propertyId= :propertyId WHERE propertyId =: id";
            entityManager.createQuery(updateQuery).setParameter("propertyId", propertyId)
                    .setParameter("id", propertyId).executeUpdate();
            logger.debug("updatePropertyId was successful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while updating the property ID: ", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updatePropertyAddress(String address, String propertyId) {
        try {
            String updateQuery = "UPDATE Property set propertyAddress= :address WHERE propertyId =: id";
            entityManager.createQuery(updateQuery).setParameter("address", address)
                    .setParameter("id", propertyId).executeUpdate();
            logger.debug("updatePropertyAddress was succesful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while updating the property address: ", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateYearOfConstruction(int yearOfConstruction, String propertyId) {
        try {
            String updateQuery = "UPDATE Property set yearOfConstruction= :yearOfConstruction WHERE propertyId =: id";
            entityManager.createQuery(updateQuery).setParameter("yearOfConstruction", yearOfConstruction)
                    .setParameter("id", propertyId).executeUpdate();
            logger.debug("updateYearOfConstruction was succesful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while updating the year of construction: ", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updatePropertyType(PropertyType propertyType, String propertyId) {
        try {
            String updateQuery = "UPDATE Property set propertyType= :propertyType WHERE propertyId =: id";
            entityManager.createQuery(updateQuery).setParameter("propertyType", propertyType)
                    .setParameter("id", propertyId).executeUpdate();
            logger.debug("updatePropertyType was succesful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while updating the property type: ", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateOwnerVat(int ownerVat, String propertyId) {
        try {
            String updateQuery = "UPDATE Property set ownerVat= :ownerVat WHERE propertyId =: id";
            entityManager.createQuery(updateQuery).setParameter("ownerVat", ownerVat)
                    .setParameter("id", propertyId).executeUpdate();
            logger.debug("updateOwnerVat was succesful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while updating the owner VAT number: ", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean safelyDelete(String propertyId) {
        try {
            String updateQuery = "UPDATE Property set isDeleted = :isDeleted WHERE propertyId = :propertyId";
            entityManager.createQuery(updateQuery)
                    .setParameter("isDeleted", true)
                    .setParameter("propertyId", propertyId).executeUpdate();
            logger.debug("safelyDelete was succesful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while safely deleting a property: ", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean permanentlyDelete(String propertyId) {
        try {
            entityManager.remove(searchPropertyId(propertyId));
            logger.debug("permanentlyDelete was succesful");
            return true;
        } catch (Exception e) {
            logger.error("An error occurred while permanently deleting a property: ", e.getMessage());
            return false;
        }
    }
}
package com.codehub.techniconrenovations.services.impl;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.UserDto;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminServicesImpl implements AdminServices {

    private static final Logger logger = LoggerFactory.getLogger(AdminServicesImpl.class);
    
    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;
    
    @Inject
    private PropertyRepository propertyRepository;
    @Inject
    private PropertyOwnerRepository propertyOwnerRepository ;
    @Inject
    private PropertyRepairRepository propertyRepairRepository;

    @Override
    public List<RepairDto> getPendingRepairs() {
        try {
            List<RepairDto> repairs = propertyRepairRepository.getPendingRepairs();
            logger.debug("Get pending repairs was succesfull");
            return repairs;
        } catch (Exception e) {
            logger.error("Error while retrieving pending repairs: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<RepairDto> getAllRepairs() {
        try {
            List<RepairDto> repairs = propertyRepairRepository.getAllRepairs();
            logger.debug("Get repairs was succesfull");
            return repairs;
        } catch (Exception e) {
            logger.error("Error while retrieving all repairs: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<PropertyDto> getProperties() {
        try {
            List<PropertyDto> properties = propertyRepository.getProperties();
            logger.debug("Get properties was succesfull");
            return properties;
        } catch (Exception e) {
            logger.error("Error while retrieving properties: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<UserDto> getOwners() {
        try {
            List<UserDto> owners = propertyOwnerRepository.getOwners();
            logger.debug("Get owners was succesfull");
            return owners;
        } catch (Exception e) {
            logger.error("Error while retrieving owners: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean proposeCost(BigDecimal cost, int repairId) {
        try {
            PropertyRepair p = propertyRepairRepository.searchById(repairId);
            if (p == null) {
                logger.error("Error repair is null: ");
                return false;
            }
            propertyRepairRepository.updateProposedCost(cost, repairId);
            logger.debug("proposeCost was updated successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while proposing cost for repair: " + e.getMessage());
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
            logger.debug("proposeStartEndDates was updated successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while proposing start and end dates for repair: " + e.getMessage());
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
            logger.debug("permanentlyDeleteProperties was successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting properties: " + e.getMessage());
            return false;
        }
    }

    /**
     * Removes all properties for a specific owner from the database.
     *
     */
    private void permanentlyDeleteRepairsList(String propertyId) {
        try {
            Property property = new Property(propertyId);
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE PropertyRepair WHERE property = :property")
                    .setParameter("property", property);
            query.executeUpdate();
            entityManager.getTransaction().commit();
            logger.debug("permanentlyDeleteRepairsList was successfully");
        } catch (Exception e) {
            logger.error("Error while deleting repairs list for property: " + e.getMessage());
        }
    }

    @Override
    public boolean permanentlyDeletePropertyRepairs() {
        try {
            List<Property> properties = entityManager.createQuery("SELECT o FROM Property o WHERE isDeleted = :isDeleted")
                    .setParameter("isDeleted", true).getResultList();
            properties.forEach(p -> permanentlyDeleteRepairsList(p.getPropertyId()));
            logger.debug("permanentlyDeletePropertyRepairs was successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting property repairs: " + e.getMessage());
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
            logger.debug("permanentlyDeleteRepairs was successfully");
        } catch (Exception e) {
            logger.error("Error while deleting repairs for owner: " + e.getMessage());
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
            logger.debug("permanentlyDeleteProperties was successfully");
        } catch (Exception e) {
            logger.error("Error while deleting properties for owner: " + e.getMessage());
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
            logger.debug("permanentlyDeletePropertyOwner was successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting property owner: " + e.getMessage());
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
            logger.debug("permanentlyDeleteRepairs was successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting property repairs: " + e.getMessage());
            return false;
        }
    }
}

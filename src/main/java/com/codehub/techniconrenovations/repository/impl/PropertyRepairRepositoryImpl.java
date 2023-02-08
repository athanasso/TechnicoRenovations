package com.codehub.techniconrenovations.repository.impl;

import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyRepairRepositoryImpl implements PropertyRepairRepository {

    private static final Logger logger = LoggerFactory.getLogger(PropertyRepairRepositoryImpl.class);

    @PersistenceContext(unitName = "Persistence")
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean createPropertyRepair(PropertyRepair propertyRepair) {
        try {
            entityManager.persist(propertyRepair);
            logger.debug("createPropertyRepair was successful");
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while creating property repair: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public void updateOwnerId(int vatNumber, int repairId) {
        try {
            PropertyRepair p = entityManager.find(PropertyRepair.class, repairId);
            p.setPropertyOwner(new PropertyOwner(vatNumber));
            createPropertyRepair(p);
            logger.debug("updateOwnerId was successful");
        } catch (Exception e) {
            logger.error("Error occurred while updating owner id: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updatePropertyId(String propertyId, int repairId) {
        try {
            PropertyRepair p = entityManager.find(PropertyRepair.class, repairId);
            p.setProperty(new Property(propertyId));
            createPropertyRepair(p);
            logger.debug("updatePropertyId was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating property id: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateRepairType(RepairType repairType, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set repairType = :repairType WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("repairType", repairType)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateRepairType was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating repair type: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateShortDescription(String shortDescription, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set shortDescription = : shortDescription WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("shortDescription", shortDescription)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateShortDescription was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating short description: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateDescription(String desciption, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set description = : desciption WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("desciption", desciption)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateDescription was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating description: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateProposedStartDate(Date proposedStartDate, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set proposedStartDate= :proposedStartDate WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("proposedStartDate", proposedStartDate)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateProposedStartDate was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating proposed start date: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateProposedEndDate(Date proposedEndDate, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set proposedEndDate= :proposedEndDate WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("proposedEndDate", proposedEndDate)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateProposedEndDate was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating proposed end date: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateProposedCost(BigDecimal proposedCost, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set proposedCost = : proposedCost WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("proposedCost", proposedCost)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateProposedCost was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating proposed cost: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateAccepted(boolean accepted, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set accepted = : accepted WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("accepted", accepted)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateAccepted was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating the accepted: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateStatus(Status status, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set status = : status WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("status", status)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateStatus was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating status: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateActualStartDate(int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set actualStartDate =  proposedStartDate WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("id", repairId).executeUpdate();
            logger.debug("updateActualStartDate was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating actual start date: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateActualEndDate(int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set actualEndDate =  proposedEndDate WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("id", repairId).executeUpdate();
            logger.debug("updateActualEndDate was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating actual end date: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateIsDeleted(boolean isDeleted, int repairId) {
        try {
            String updateQuery = "UPDATE PropertyRepair set isDeleted = : isDeleted WHERE repairId =: id";
            entityManager.createQuery(updateQuery).setParameter("isDeleted", isDeleted)
                    .setParameter("id", repairId).executeUpdate();
            logger.debug("updateIsDeleted was succesful");
        } catch (Exception e) {
            logger.error("Error occurred while updating isDeleted: {}", e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByDate(Date date) {
        try {
            return entityManager.createQuery("SELECT o FROM PropertyRepair o WHERE o.actualStartDate = :date OR o.actualEndDate= :date OR o.date= :date", PropertyRepair.class)
                    .setParameter("date", date).getResultList();
        } catch (Exception e) {
            logger.error("Error searching for property owner by Date: " + e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByRangeOfDates(Date rangeStartDate, Date rangeEndDate) {
        try {
            return entityManager.createQuery("SELECT o FROM PropertyRepair o WHERE actualStartDate>=:rangeStartDate AND actualEndDate<=:rangeEndDate")
                    .setParameter("rangeStartDate", rangeStartDate)
                    .setParameter("rangeEndDate", rangeEndDate)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error searching for property owner by by range of dates: " + e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByOwnerVat(int vatNumber) {
        try {
            return entityManager.createQuery("SELECT o FROM PropertyRepair o INNER JOIN PropertyOwner p ON o.propertyOwner = p.vatNumber WHERE o.isDeleted = false AND p.vatNumber = " + vatNumber)
                    .getResultList();
        } catch (Exception e) {
            logger.error("Error searching for property owner by VAT number: " + e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public PropertyRepair searchById(int repairId) {
        try {
            return entityManager.find(PropertyRepair.class, repairId);
        } catch (Exception e) {
            logger.error("Error searching for property owner by repair id: " + e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public List<PropertyRepair> searchByPropertyId(String propertyId) {
        try {
            return entityManager.createQuery("SELECT p FROM PropertyRepair p INNER JOIN Property o ON p.property = o.propertyId WHERE o.isDeleted = FAlSE AND  o.propertyId =:propertyId")
                    .setParameter("propertyId", propertyId).getResultList();
        } catch (Exception e) {
            logger.error("Error searching for property owner by property id: " + e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public boolean safelyDelete(int repairId) {
        try {
            updateIsDeleted(true, repairId);
            logger.debug("safelyDelete was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while safely deleting: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public boolean permanentlyDelete(int repairId) {
        try {
            entityManager.createQuery("DELETE PropertyRepair WHERE repairId = :repairId")
                    .setParameter("repairId", repairId).executeUpdate();
            logger.debug("permanentlyDelete was succesful");
            return true;
        } catch (Exception e) {
            logger.error("Error occurred while permamently deleting: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public List<RepairDto> getPendingRepairs() {
        try {
            List<RepairDto> repairDtoList = entityManager.createQuery("SELECT r FROM PropertyRepair r WHERE r.status = :status")
                    .setParameter("status", Status.PENDING)
                    .getResultList();
            return repairDtoList;
        } catch (Exception e) {
            logger.error("Error while retrieving pending repairs: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<RepairDto> getAllRepairs() {
        try {
            List<RepairDto> repairs = entityManager.createQuery("SELECT r FROM PropertyRepair r")
                    .getResultList();
            return repairs;
        } catch (Exception e) {
            logger.error("Error while retrieving all repairs: " + e.getMessage());
            return null;
        }
    }
}

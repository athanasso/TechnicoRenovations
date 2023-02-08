package com.codehub.techniconrenovations.services.impl;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.UserDto;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import com.codehub.techniconrenovations.services.AdminServices;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdminServicesImpl implements AdminServices {

    private static final Logger logger = LoggerFactory.getLogger(AdminServicesImpl.class);
    
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
            propertyRepairRepository.permanentlyDeleteRepairs();
            propertyRepository.permanentlyDeleteProperties();
            logger.debug("permanentlyDeleteProperties was successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting properties: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean permanentlyDeletePropertyOwner() {
        try {
            propertyOwnerRepository.permanentlyDeletePropertyOwner();
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
            propertyRepairRepository.permanentlyDeleteRepairs();
            logger.debug("permanentlyDeleteRepairs was successfully");
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting property repairs: " + e.getMessage());
            return false;
        }
    }
}

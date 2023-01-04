package com.codehub.techniconrenovations.services.impl;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.repository.PropertyOwnerRepository;
import com.codehub.techniconrenovations.repository.PropertyRepairRepository;
import com.codehub.techniconrenovations.repository.PropertyRepository;
import com.codehub.techniconrenovations.services.PropertyOwnerServices;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyOwnerServicesImpl implements PropertyOwnerServices {

    private static final Logger logger = LoggerFactory.getLogger(PropertyOwnerServicesImpl.class);
    
    @Inject
    private PropertyRepository propertyRepository;
    @Inject
    private PropertyOwnerRepository propertyOwnerRepository ;
    @Inject
    private PropertyRepairRepository propertyRepairRepository;

    @Override
    public List<Property> getProperties(int vatNumber) {
        try {
            List<Property> pr = propertyRepository.searchVat(vatNumber);
            List<Property> properties = new ArrayList<>();
            pr.forEach(p -> {
                if (!p.isIsDeleted()) {
                    properties.add(p);
                }
            });
            if (properties.isEmpty()) {
                logger.error("properties are empty error 401");
                return null;
            }
            logger.debug("return properties was succesfull");
            return properties;
        } catch (Exception e) {
            logger.error("An error occurred while getting properties error 500: ", e);
            return null;
        }
    }

    @Override
    public List<PropertyRepair> getRepairStatus(int vatNumber) {
        try {
            List<Property> properties = propertyRepository.searchVat(vatNumber);
            List<PropertyRepair> repairs = new ArrayList<>();
            for (Property p : properties) {
                List<PropertyRepair> r = propertyRepairRepository.searchByPropertyId(p.getPropertyId());
                r.forEach(pr -> {
                    if (!pr.isIsDeleted()) {
                        repairs.add(pr);
                    }
                });
            }
            if (repairs.isEmpty()) {
                logger.error("properties are empty error 401");
                return null;
            }
            logger.debug("return repair statuses was succesfull");
            return repairs;
        } catch (Exception e) {
            logger.error("An error occurred while getting properties error 500: ", e);
            return null;
        }
    }

    @Override
    public boolean register(int vatNumber, String name, String surname, String address, String phoneNumber, String email, String username, String password, String typeOfUser) {
        try {
            PropertyOwner propertyOwner = new PropertyOwner();
            propertyOwner.setVatNumber(vatNumber);
            propertyOwner.setName(name);
            propertyOwner.setSurname(surname);
            propertyOwner.setAddress(address);
            propertyOwner.setPhoneNumber(phoneNumber);
            propertyOwner.setEmail(email);
            propertyOwner.setUsername(username);
            propertyOwner.setPassword(password);
            propertyOwner.setTypeOfUser(typeOfUser);
            logger.debug("owner register was succesfull");
            return propertyOwnerRepository.createPropertyOwner(propertyOwner);
        } catch (jakarta.persistence.RollbackException ex) {
            logger.error("An error occurred while registering a property owner: ", ex);
            return false;
        } catch (Exception e) {
            logger.error("An error occurred while registering a property owner: ", e);
            return false;
        }
    }

    @Override
    public boolean registerProperty(int vatNumber, String e9, String address, int constructionYear, PropertyType propertyType) {
        try {
            Property property = new Property();
            String propertyId = e9;
            if (propertyRepository.searchPropertyId(propertyId) != null) {
                logger.error("property is already registered");
                return false;
            }
            property.setPropertyId(propertyId);
            property.setPropertyAddress(address);
            property.setYearOfConstruction(constructionYear);
            property.setPropertyOwner(new PropertyOwner(vatNumber));
            property.setPropertyType(propertyType);
            propertyRepository.createProperty(property);
            logger.debug("property was registered succefully!");
            return true;
        } catch (Exception e) {
            logger.error("Error registering property: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean registerPropertyRepair(int vatNumber, String e9, String description, String shortDescription, RepairType repairType) {
        try {
            PropertyRepair propertyRepair = new PropertyRepair();
            propertyRepair.setPropertyOwner(new PropertyOwner(vatNumber));
            propertyRepair.setProperty(new Property(e9));
            propertyRepair.setDate(new Date());
            propertyRepair.setDesciption(description);
            propertyRepair.setShortDescription(shortDescription);
            propertyRepair.setRepairType(repairType);
            propertyRepair.setStatus(Status.PENDING);
            propertyRepairRepository.createPropertyRepair(propertyRepair);
            logger.debug("repair was registered succefully");
            return true;
        } catch (Exception e) {
            logger.error("Error registering property repair: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean acceptOrDeclineRepair(int vatNumber, int repairId, boolean acceptStatus) {
        try {
            if (!checkRepair(repairId, vatNumber)) {
                logger.error("not found");
                return false;
            }
            PropertyRepair propertyRepair = propertyRepairRepository.searchById(repairId);
            if (propertyRepair.getProposedStartDate() == null) {
                logger.error("can't accept this repair yet, we haven't proposed dates and cost");
                return false;
            }

            propertyRepairRepository.updateAccepted(acceptStatus, repairId);
            if (!acceptStatus) {
                propertyRepairRepository.updateStatus(Status.DECLINED, repairId);
            } else {
                propertyRepairRepository.updateStatus(Status.IN_PROGRESS, repairId);
                propertyRepairRepository.updateActualStartDate(repairId);
                propertyRepairRepository.updateActualEndDate(repairId);
            }
            logger.debug("acceptOrDeclineRepair was succeful!");
            return true;
        } catch (Exception e) {
            logger.error("Error accepting or declining repair: " + e.getMessage());
            return false;
        }
    }

    @Override
    public PropertyOwner logIn(String username, String password) {
        try {
            logger.debug("user login was succeful");
            return propertyOwnerRepository.searchByUsernameAndPassword(username, password);
        } catch (Exception e) {
            logger.error("Error logging in: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean correctPropertyAddress(int vatNumber, String propertyId, String address) {
        try {
            if (!checkProperty(propertyId, vatNumber)) {
                logger.error("not found");
                return false;
            }
            propertyRepository.updatePropertyAddress(address, propertyId);
            logger.debug("correctPropertyAddress was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error correcting property address: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean correctPropertyconstructionYear(int vatNumber, String propertyId, int constructionYear) {
        try {
            if (!checkProperty(propertyId, vatNumber)) {
                logger.error("not found");
                return false;
            }
            propertyRepository.updateYearOfConstruction(constructionYear, propertyId);
            logger.debug("correctPropertyconstructionYear was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error correcting property construction year: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean correctPropertyType(int vatNumber, String propertyId, PropertyType propertyType) {
        try {
            if (!checkProperty(propertyId, vatNumber)) {
                logger.error("not found");
                return false;
            }
            propertyRepository.updatePropertyType(propertyType, propertyId);
            logger.debug("correctPropertyType was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error correcting property type: " + e.getMessage());
            return false;
        } 
    }

    @Override
    public boolean correctOwnerUsername(int vatNumber, String username) {
        try {
            propertyOwnerRepository.updateUsername(username, vatNumber);
            logger.debug("correctOwnerUsername was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error correcting owner username: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean correctOwnerEmail(int vatNumber, String email) {
        try {
            propertyOwnerRepository.updateEmail(email, vatNumber);
            logger.debug("correctOwnerEmail was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error correcting owner email: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean correctOwnerPassword(int vatNumber, String password) {
        try {
            propertyOwnerRepository.updatePassword(password, vatNumber);
            logger.debug("correctOwnerPassword was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error correcting owner password: " + e.getMessage());
            return false;
        }
    }

    @Override
    public int getUserVat(String username, String password) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.searchByUsernameAndPassword(username, password);
            logger.debug("getUserVat was succeful");
            return propertyOwner.getVatNumber();
        } catch (Exception e) {
            logger.error("Error getting user VAT number", e);
            return -1;
        }
    }

    private boolean checkProperty(String propertyId, int vatNumber) {
        try {
            Property property = propertyRepository.searchPropertyId(propertyId);
            if (property == null) {
                logger.error("Property does not exist in database");
                return false;
            }
            if (property.getPropertyOwner().getVatNumber() != vatNumber) {
                logger.error("Property is not owned by user with VAT number " + vatNumber);
                return false;
            }
            logger.debug("checkProperty was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error checking property", e);
            return false;
        }
    }

    private boolean checkRepair(int repairId, int vatNumber) {
        try {
            PropertyRepair propertyRepair = propertyRepairRepository.searchById(repairId);
            if (propertyRepair == null) {
                logger.error("Repair does not exist in database");
                return false;
            }
            if (propertyRepair.getPropertyOwner().getVatNumber() != vatNumber) {
                logger.error("Repair is not owned by user with VAT number " + vatNumber);
                return false;
            }
            logger.debug("checkRepair was succeful");
            return true;
        } catch (Exception e) {
            logger.error("Error checking repair", e);
            return false;
        }
    }

    @Override
    public boolean safelyDeleteProperty(int vatNumber, String e9) {
        try {
            if (checkProperty(e9, vatNumber)) {
                logger.debug("safelyDeleteProperty was succeful");
                return propertyRepository.safelyDelete(e9);
            } else {
                logger.debug("property does not exist");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error safely deleting property", e);
            return false;
        }
    }

    @Override
    public boolean safelyDeletePropertyRepair(int vatNumber, int repairId) {
        try {
            if (checkRepair(repairId, vatNumber)) {
                logger.debug("safelyDeleteProperty was succeful");
                return propertyRepairRepository.safelyDelete(repairId);
            } else {
                logger.error("property repair does not exist");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error safely deleting property repair", e);
            return false;
        }
    }

    @Override
    public boolean safelyDeletePropertyOwner(int vatNumber) {
        try {
            if (propertyOwnerRepository.safelyDelete(vatNumber)) {
                logger.debug("User with VAT: " + vatNumber + " succesfully deleted");
                return true;
            }
            logger.error("User delete failed");
            return false;
        } catch (Exception e) {
            logger.error("Error safely deleting property owner", e);
            return false;
        }
    }
}

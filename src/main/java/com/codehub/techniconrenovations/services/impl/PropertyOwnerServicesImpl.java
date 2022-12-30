package com.codehub.techniconrenovations.services.impl;

import com.codehub.techniconrenovations.dto.RestApiResult;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PropertyOwnerServicesImpl implements PropertyOwnerServices {

    private final PropertyRepository propertyRepository;
    private final PropertyOwnerRepository propertyOwnerRepository;
    private final PropertyRepairRepository propertyRepairRepository;

    @Inject
    public PropertyOwnerServicesImpl(PropertyRepository propertyRepository, PropertyOwnerRepository propertyOwnerRepository, PropertyRepairRepository propertyRepairRepository) {
        this.propertyRepository = propertyRepository;
        this.propertyOwnerRepository = propertyOwnerRepository;
        this.propertyRepairRepository = propertyRepairRepository;
    }

    @Override
    public RestApiResult<List<Property>> getProperties(int vatNumber) {
        List<Property> pr = propertyRepository.searchVat(vatNumber);
        List<Property> properties = new ArrayList<>();
        pr.forEach(p -> {
            if (!p.isIsDeleted()) {
                properties.add(p);
            }
        });
        if (properties.isEmpty()){
            return new RestApiResult<>(null, 401, "Something went wrong!");
        }
       return new RestApiResult<>(properties, 200, "Successful!");
    }

    @Override
    public RestApiResult<List<PropertyRepair>> getRepairStatus(int vatNumber) {
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
        if (repairs.isEmpty()){
            return new RestApiResult<>(null, 401, "Something went wrong!");
        }
        return new RestApiResult<>(repairs, 200, "Successful!");
    }

    @Override
    public boolean register(int vatNumber, String name, String surname, String address, String phoneNumber, String email, String username, String password) {
        PropertyOwner propertyOwner = new PropertyOwner();
        propertyOwner.setVatNumber(vatNumber);
        propertyOwner.setName(name);
        propertyOwner.setSurname(surname);
        propertyOwner.setAddress(address);
        propertyOwner.setPhoneNumber(phoneNumber);
        propertyOwner.setEmail(email);
        propertyOwner.setUsername(username);
        propertyOwner.setPassword(password);
        try {
            return propertyOwnerRepository.createPropertyOwner(propertyOwner); 
        } catch (jakarta.persistence.RollbackException ex) {
            return false;
        }
    }

    @Override
    public void registerProperty(int vatNumber, String e9, String address, int constructionYear, PropertyType propertyType) {
        Property property = new Property();
        String propertyId = e9;
        if (propertyRepository.searchPropertyId(propertyId)!=null) {
            //This property is already registered
            return;
        }
        property.setPropertyId(propertyId);
        property.setPropertyAddress(address);
        property.setYearOfConstruction(constructionYear);
        property.setPropertyOwner(new PropertyOwner(vatNumber));
        property.setPropertyType(propertyType);
        if (propertyRepository.createProperty(property)) {
            //Your property was registered succefully!
        } else {
            //There was a problem registering your repair because it is already registered.
        }
    }

    @Override
    public void registerPropertyRepair(int vatNumber, String e9, String description, String shortDescription, RepairType repairType) {
        PropertyRepair propertyRepair = new PropertyRepair();
        propertyRepair.setPropertyOwner(new PropertyOwner(vatNumber));
        propertyRepair.setProperty(new Property(e9));
        propertyRepair.setDate(new Date());
        propertyRepair.setDesciption(description);
        propertyRepair.setShortDescription(shortDescription);
        propertyRepair.setRepairType(repairType);
        propertyRepair.setStatus(Status.PENDING);
        if (propertyRepairRepository.createPropertyRepair(propertyRepair)) {
            //Your repair was registered succefully
        }
    }

    @Override
    public void acceptOrDeclineRepair(int vatNumber, int repairId, boolean acceptStatus) {
        if (!checkRepair(repairId, vatNumber)) {
            return;
        }
        PropertyRepair propertyRepair = propertyRepairRepository.searchById(repairId);
        if (propertyRepair.getProposedStartDate()==null){
            //You can't accept this repair yet, we haven't proposed dates and cost
            return;
        }
       
        propertyRepairRepository.updateAccepted(acceptStatus, repairId);
        if (!acceptStatus) {
            propertyRepairRepository.updateStatus(Status.DECLINED, repairId);
        } else {
            propertyRepairRepository.updateStatus(Status.IN_PROGRESS, repairId);
            propertyRepairRepository.updateActualStartDate(repairId);
            propertyRepairRepository.updateActualEndDate(repairId);
        }
    }

    @Override
    public boolean logIn(String username, String password) {
        return propertyOwnerRepository.searchByUsernameAndPassword(username, password) != null;
    }

    @Override
    public void correctPropertyAddress(int vatNumber, String propertyId, String address) {
        if (!checkProperty(propertyId, vatNumber)) {
            return;
        }
        propertyRepository.updatePropertyAddress(address, propertyId);
    }
    
    
    @Override
    public void correctPropertyconstructionYear(int vatNumber, String propertyId, int constructionYear) {
        if (!checkProperty(propertyId, vatNumber)) {
            return;
        }    
        propertyRepository.updateYearOfConstruction(constructionYear, propertyId);
    }

    @Override
    public void correctPropertyType(int vatNumber, String propertyId, PropertyType propertyType) {
        if (!checkProperty(propertyId, vatNumber)) {
            return;
        }  
        propertyRepository.updatePropertyType(propertyType, propertyId);
    }
    
    @Override
    public void correctOwnerUsername(int vatNumber, String username) {
        propertyOwnerRepository.updateUsername(username, vatNumber);
    }

    @Override
    public void correctOwnerEmail(int vatNumber, String email) {
        propertyOwnerRepository.updateEmail(email, vatNumber);
    }

    @Override
    public void correctOwnerPassword(int vatNumber, String password) {
        propertyOwnerRepository.updatePassword(password, vatNumber);
    }

    @Override
    public int getUserVat(String username, String password) {
        PropertyOwner propertyOwner = propertyOwnerRepository.searchByUsernameAndPassword(username, password);
        return propertyOwner.getVatNumber();
    }

    private boolean checkProperty(String propertyId, int vatNumber) {
        Property property = propertyRepository.searchPropertyId(propertyId);
        if (property == null) {
            System.err.println("This property does not exist in our data base!");
            return false;
        }
        if (property.getPropertyOwner().getVatNumber() != vatNumber) {
            System.err.println("This property is not yours!");
            return false;
        }
        return true;
    }

    private boolean checkRepair(int repairId, int vatNumber) {
        PropertyRepair propertyRepair = propertyRepairRepository.searchById(repairId);
        if (propertyRepair == null) {
            System.err.println("This repair does not exist in our data base!");
            return false;
        }
        if (propertyRepair.getPropertyOwner().getVatNumber() != vatNumber) {
            System.err.println("This repair is not yours!");
            return false;
        }
        return true;
    }

    @Override
    public boolean safelyDeleteProperty(int vatNumber, String e9) {
        if (checkProperty(e9, vatNumber)) {
            return propertyRepository.safelyDelete(e9);
        } else {
            return false;
        }
    }

    @Override
    public boolean safelyDeletePropertyRepair(int vatNumber, int repairId) {
        if (checkRepair(repairId, vatNumber)) {
            return propertyRepairRepository.safelyDelete(repairId);
        } else {
            return false;
        }
    }

    @Override
    public boolean safelyDeletePropertyOwner(int vatNumber) {
        if (propertyOwnerRepository.safelyDelete(vatNumber)) {
            System.out.println("User with VAT: " + vatNumber + " succesfully deleted");
            return true;
        }
        System.err.println("User delete failed");
        return false;
    }
}

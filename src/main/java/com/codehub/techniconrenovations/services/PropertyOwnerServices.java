package com.codehub.techniconrenovations.services;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.model.PropertyOwner;
import java.util.List;

public interface PropertyOwnerServices {

    /**
     * Returns a list with the owner's Properties.
     *
     * @param vatNumber
     * @return List<PropertyDto>
     */
    List<PropertyDto> getProperties(int vatNumber);

    /**
     * Returns the status of a repair.
     *
     * @param vatNumber
     * @return List<RepairDto>
     */
    List<RepairDto> getRepairStatus(int vatNumber);

    /**
     * Registers a new PropertyOwner to the database.
     * @param vatNumber
     * @param name
     * @param email
     * @param surname
     * @param phoneNumber
     * @param address
     * @param username
     * @param password
     * @param typeOfUser
     * @return boolean
     */
    boolean register(int vatNumber, String name, String surname, String address, String phoneNumber, String email, String username, String password, String typeOfUser);

    /**
     * Registers a new Property to the database.
     * @param vatNumber
     * @param e9
     * @param address
     * @param constructionYear
     * @param propertyType
     * @return 
     */
    boolean registerProperty(int vatNumber, String e9, String address, int constructionYear, PropertyType propertyType);

    /**
     * Registers a new PropertyRepair to the database.
     * @param vatNumber
     * @param e9
     * @param description
     * @param shortDescription
     * @param repairType
     * @return 
     */
    boolean registerPropertyRepair(int vatNumber, String e9, String description, String shortDescription, RepairType repairType);

    /**
     * Updates whether the owner accepts the Repair or not.
     *
     * @param vatNumber
     * @param repairId
     * @param acceptStatus
     * @return 
     */
    boolean acceptOrDeclineRepair(int vatNumber, int repairId, boolean acceptStatus);
    
    /**
     *
     * @param vatNumber
     * @param repairId
     * @param description
     * @return
     */
    boolean changeDescription(int vatNumber, int repairId, String description);

    /**
     * Returns true if the credentials are correct.
     *
     * @param username
     * @param password
     * @return PropertyOwner
     */
    PropertyOwner logIn(String username, String password);

    /**
     * Updates a property's credentials.
     * @param vatNumber
     * @param propertyId
     * @param address
     * @return 
     */
    boolean correctPropertyAddress(int vatNumber, String propertyId, String address);
    
    /**
     *
     * @param vatNumber
     * @param propertyId
     * @param constructionYear
     * @return 
     */
    boolean correctPropertyConstructionYear(int vatNumber, String propertyId, int constructionYear);
    
    /**
     *
     * @param vatNumber
     * @param propertyId
     * @param propertyType
     * @return 
     */
    boolean correctPropertyType(int vatNumber, String propertyId, PropertyType propertyType);

    /**
     * Updates a property owner's credentials.
     * @param vatNumber
     * @param username
     * @return 
     */
    boolean correctOwnerUsername(int vatNumber, String username);
    
    /**
     *
     * @param vatNumber
     * @param email
     * @return 
     */
    boolean correctOwnerEmail(int vatNumber, String email);
    
    /**
     *
     * @param vatNumber
     * @param password
     * @return 
     */
    boolean correctOwnerPassword(int vatNumber, String password);
    
    /**
     * Returns the vat Number of a user.
     * @param username
     * @param password
     * @return vatNumber
     */
    int getUserVat(String username, String password);
    
    /**
     *
     * @param vatNumber
     * @param e9
     * @return
     */
    boolean safelyDeleteProperty(int vatNumber, String e9);
    
    /**
     *
     * @param vatNumber
     * @param repairId
     * @return
     */
    boolean safelyDeletePropertyRepair(int vatNumber, int repairId);

    /**
     *
     * @param myVatNumber
     * @return
     */
    boolean safelyDeletePropertyOwner(int myVatNumber);
}

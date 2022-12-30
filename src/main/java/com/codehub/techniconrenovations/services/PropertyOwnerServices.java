package com.codehub.techniconrenovations.services;

import com.codehub.techniconrenovations.dto.RestApiResult;
import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyRepair;
import java.util.List;

public interface PropertyOwnerServices {

    /**
     * Returns a list with the owner's Properties.
     *
     * @param vatNumber
     * @return List<Property>
     */
    RestApiResult<List<Property>> getProperties(int vatNumber);

    /**
     * Returns the status of a repair.
     *
     * @param vatNumber
     * @return List<PropertyRepair>
     */
    RestApiResult<List<PropertyRepair>> getRepairStatus(int vatNumber);

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
     * @return boolean
     */
    boolean register(int vatNumber, String name, String surname, String address, String phoneNumber, String email, String username, String password);

    /**
     * Registers a new Property to the database.
     * @param vatNumber
     * @param e9
     * @param address
     * @param constructionYear
     * @param propertyType
     */
    void registerProperty(int vatNumber, String e9, String address, int constructionYear, PropertyType propertyType);

    /**
     * Registers a new PropertyRepair to the database.
     * @param vatNumber
     * @param e9
     * @param description
     * @param shortDescription
     * @param repairType
     */
    void registerPropertyRepair(int vatNumber, String e9, String description, String shortDescription, RepairType repairType);

    /**
     * Updates whether the owner accepts the Repair or not.
     *
     * @param vatNumber
     * @param repairId
     * @param acceptStatus
     */
    void acceptOrDeclineRepair(int vatNumber, int repairId, boolean acceptStatus);

    /**
     * Returns true if the credentials are correct.
     *
     * @param username
     * @param password
     * @return boolean
     */
    boolean logIn(String username, String password);

    /**
     * Updates a property's credentials.
     * @param vatNumber
     * @param propertyId
     * @param address
     */
    void correctPropertyAddress(int vatNumber, String propertyId, String address);
    
    /**
     *
     * @param vatNumber
     * @param propertyId
     * @param constructionYear
     */
    void correctPropertyconstructionYear(int vatNumber, String propertyId, int constructionYear);
    
    /**
     *
     * @param vatNumber
     * @param propertyId
     * @param propertyType
     */
    void correctPropertyType(int vatNumber, String propertyId, PropertyType propertyType);

    /**
     * Updates a property owner's credentials.
     * @param vatNumber
     * @param username
     */
    void correctOwnerUsername(int vatNumber, String username);
    
    /**
     *
     * @param vatNumber
     * @param email
     */
    void correctOwnerEmail(int vatNumber, String email);
    
    /**
     *
     * @param vatNumber
     * @param password
     */
    void correctOwnerPassword(int vatNumber, String password);
    
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

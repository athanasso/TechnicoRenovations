package com.codehub.techniconrenovations.repository;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.model.Property;
import java.util.List;

public interface PropertyRepository {

    /**
     *
     * @param property
     * @return
     */
    boolean createProperty(Property property);

    /**
     * Returns a property with the given id.
     * @param propertyId
     * @return
     */
    Property searchPropertyId(String propertyId);

    /**
     * Returns a list with all the properties owner by a single owner.
     * @param ownerVat
     * @return
     */
    List<Property> searchVat(int ownerVat);

    /**
     * Updates the id of a property.
     * @param newPropertyId
     * @param propertyId
     * @return
     */
    boolean updatePropertyId(String newPropertyId, String propertyId);

    /**
     * Updates the address of a property.
     * @param address
     * @param propertyId
     * @return
     */
    boolean updatePropertyAddress(String address, String propertyId);

    /**
     * Updates the year a property was constructed.
     * @param yearOfConstruction
     * @param propertyId
     * @return
     */
    boolean updateYearOfConstruction(int yearOfConstruction, String propertyId);

    /**
     * Updates the type of a property.
     * @param propertyType
     * @param propertyId
     * @return
     */
    boolean updatePropertyType(PropertyType propertyType, String propertyId);

    /**
     * Updates property owner's vatNumber.
     * @param ownerVat
     * @param propertyId
     * @return
     */
    boolean updateOwnerVat(int ownerVat, String propertyId);

    /**
     * Updates the address of an owner.
     * @param propertyId
     * @return
     */
    boolean safelyDelete(String propertyId);

    /**
     * Updates the address of an owner.
     * @param propertyId
     * @return
     */
    boolean permanentlyDelete(String propertyId);
}

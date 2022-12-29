package com.codehub.techniconrenovations.repository;

import com.codehub.techniconrenovations.model.PropertyOwner;

public interface PropertyOwnerRepository {

    /**
     *
     * @param propertyOwner
     * @return
     */
    boolean createPropertyOwner(PropertyOwner propertyOwner)throws jakarta.persistence.RollbackException;

    /**
     *
     * @param vatNumber
     * @return
     */
    PropertyOwner searchVat(int vatNumber);

    /**
     *
     * @param email
     * @return
     */
    PropertyOwner searchEmail(String email);

    /**
     * Returns a property owner with the given username and password, null if
     * not existent.
     *
     * @param username
     * @param password
     * @return PropertyOwner
     */
    PropertyOwner searchByUsernameAndPassword(String username, String password);

    /**
     * Updates the address of an owner.
     *
     * @param address
     * @param vatNumber
     * @return
     */
    boolean updateAddress(String address, int vatNumber);

    /**
     * Updates the email of an owner.
     *
     * @param email
     * @param vatNumber
     * @return
     */
    boolean updateEmail(String email, int vatNumber);

    /**
     * Updates the password of an owner.
     *
     * @param password
     * @param vatNumber
     * @return
     */
    boolean updatePassword(String password, int vatNumber);

    /**
     * Updates the username of an owner.
     *
     * @param username
     * @param vatNumber
     * @return
     */
    boolean updateUsername(String username, int vatNumber);

    /**
     * Marks an owner as deleted.
     *
     * @param vatNumber
     * @return
     */
    boolean safelyDelete(int vatNumber);

    /**
     * Updates an owner.
     *
     * @param vatNumber
     * @return
     */
    boolean permanentlyDelete(int vatNumber);
}

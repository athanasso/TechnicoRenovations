package com.codehub.techniconrenovations.repository;

import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.PropertyRepair;
import java.util.Date;
import java.util.List;

public interface PropertyRepairRepository {

    /**
     * adds a new PropertyRepair.
     *
     * @param propertyRepair
     * @return bool
     */
    boolean createPropertyRepair(PropertyRepair propertyRepair);

    /**
     * Updates ownerId.
     *
     * @param ownerId
     * @param repairId
     */
    void updateOwnerId(int ownerId, int repairId);

    /**
     * Updates propertyId.
     *
     * @param propertyId
     * @param repairId
     */
    void updatePropertyId(String propertyId, int repairId);

    /**
     * Updates the repairType.
     *
     * @param repairType
     */
    void updateRepairType(RepairType repairType, int repairId);

    /**
     * Updates the shortDescription.
     *
     * @param shortDescription
     * @param repairId
     */
    void updateShortDescription(String shortDescription, int repairId);

    /**
     * Updates the desciption.
     *
     * @param desciption
     * @param repairId
     */
    void updateDescription(String desciption, int repairId);

    /**
     * Updates the starting date proposed by the company.
     *
     * @param proposedStartDate
     * @param repairId
     */
    void updateProposedStartDate(Date proposedStartDate, int repairId);

    /**
     * Updates the ending date proposed by the company.
     *
     * @param proposedEndDate
     * @param repairId
     */
    void updateProposedEndDate(Date proposedEndDate, int repairId);

    /**
     * Updates the proposed cost by the company.
     *
     * @param proposedCost
     * @param repairId
     */
    void updateProposedCost(Double proposedCost, int repairId);

    /**
     * Updates if the repair is accepted by the owner.
     *
     * @param accepted
     * @param repairId
     */
    void updateAccepted(boolean accepted, int repairId);

    /**
     * Updates the repair's status.
     *
     * @param status
     * @param repairId
     */
    void updateStatus(Status status, int repairId);

    /**
     * Updates the date the repair will end.
     *
     * @param repairId
     */
    void updateActualStartDate(int repairId);

    /**
     *
     * @param repairId
     */
    void updateActualEndDate(int repairId);

    /**
     * Updates the deleted flag
     *
     * @param isDeleted
     * @param repairId
     */
    void updateIsDeleted(boolean isDeleted, int repairId);

    /**
     * Search for an owner's property repairs.
     *
     * @param date
     * @return PropertyRepair
     */
    List<PropertyRepair> searchByDate(Date date);

    /**
     * Search for all PropertyRepair in a specific date.
     *
     * @param rangeStartDate
     * @param rangeEndDate
     * @return PropertyRepair
     */
    List<PropertyRepair> searchByRangeOfDates(Date rangeStartDate, Date rangeEndDate);

    /**
     * Search for all PropertyRepair in a range of dates.
     *
     * @param vatNumber
     * @return
     */
    List<PropertyRepair> searchByOwnerVat(int vatNumber);

    /**
     * Returns the Property Repair with the current id.
     *
     * @param repairId
     * @return
     */
    PropertyRepair searchById(int repairId);

    /**
     * Returns the Repair status for a property
     *
     * @param propertyId
     * @return Status
     */
    List<PropertyRepair> searchByPropertyId(String propertyId);

    /**
     * Safely removes a PropertyRepair.
     *
     * @param repairId
     * @return bool
     */
    boolean safelyDelete(int repairId);

    /**
     * Permanently removes a PropertyRepair.
     *
     * @param repairId
     * @return bool
     */
    boolean permanentlyDelete(int repairId);
}

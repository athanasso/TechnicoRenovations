package com.codehub.techniconrenovations.services;

import com.codehub.techniconrenovations.dto.PropertyDto;
import com.codehub.techniconrenovations.dto.RepairDto;
import com.codehub.techniconrenovations.dto.UserDto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AdminServices {

    /**
     * Returns all the Repairs with pending status.
     *
     * @return List<RepairDto>
     */
    List<RepairDto> getPendingRepairs();

    /**
     * Returns all the Repairs.
     *
     * @return List<RepairDto>
     */
    List<RepairDto> getAllRepairs();

    /**
     * Returns all registered properties.
     *
     * @return List<PropertyDto>
     */
    List<PropertyDto> getProperties();

    /**
     * Returns all registered owners.
     *
     * @return List<UserDto>
     */
    List<UserDto> getOwners();

    /**
     * Proposes a cost for a Property Repair.
     *
     * @param cost
     * @param repairId
     * @return boolean
     */
    boolean proposeCost(BigDecimal cost, int repairId);

    /**
     * Proposes the starting and ending dates for a Property Repair.
     *
     * @param startDate
     * @param endDate
     * @param repairId
     * @return
     */
    boolean proposeStartEndDates(Date startDate, Date endDate, int repairId);

    /**
     * Removes deleted properties from the database.
     * @return 
     */
    boolean permanentlyDeleteProperties();

    /**
     * Removes deleted owners from the database.
     * @return 
     */
    boolean permanentlyDeletePropertyOwner();

    /**
     * Removes deleted repairs from the database.
     * @return 
     */
    boolean permanentlyDeleteRepairs();

}

package com.codehub.techniconrenovations.dto;

import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.codehub.techniconrenovations.util.UtilFunctions;
import java.math.BigDecimal;

public class RepairDto {

    private int repairId;
    private RepairType repairType;
    private PropertyOwner propertyOwner;
    private Property property;
    private String date;
    private String shortDescription;
    private String proposedStartDate;
    private String proposedEndDate;
    private BigDecimal proposedCost;
    private String desciption;
    private boolean accepted;
    private Status status;
    private String actualStartDate;
    private String actualEndDate;
    private boolean isDeleted;

    public RepairDto(PropertyRepair repair) {
        if (repair != null) {
            repairId = repair.getRepairId();
            propertyOwner = repair.getPropertyOwner();
            property = repair.getProperty();
            repairType = repair.getRepairType();
            date = UtilFunctions.dateToString(repair.getDate());
            shortDescription = repair.getShortDescription();
            proposedStartDate = UtilFunctions.dateToString(repair.getProposedStartDate());
            proposedEndDate = UtilFunctions.dateToString(repair.getProposedEndDate());
            proposedCost = repair.getProposedCost();
            desciption = repair.getDesciption();
            accepted = repair.isAccepted();
            status = repair.getStatus();
            actualStartDate = UtilFunctions.dateToString(repair.getActualStartDate());
            actualEndDate = UtilFunctions.dateToString(repair.getActualEndDate());
            isDeleted = repair.isIsDeleted();
        }
    }

    public PropertyRepair asRepair() {
        PropertyRepair repair = new PropertyRepair();
        repair.setRepairId(repairId);
        repair.setPropertyOwner(propertyOwner);
        repair.setProperty(property);
        repair.setRepairType(repairType);
        repair.setDate(UtilFunctions.stringToDate(date));
        repair.setShortDescription(shortDescription);
        repair.setProposedStartDate(UtilFunctions.stringToDate(proposedStartDate));
        repair.setProposedEndDate(UtilFunctions.stringToDate(proposedEndDate));
        repair.setProposedCost(proposedCost);
        repair.setDesciption(desciption);
        repair.setAccepted(accepted);
        repair.setStatus(status);
        repair.setActualStartDate(UtilFunctions.stringToDate(actualStartDate));
        repair.setActualEndDate(UtilFunctions.stringToDate(actualEndDate));
        repair.setIsDeleted(isDeleted);
        return repair;
    }

    public RepairDto() {
    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
    }

    public RepairType getRepairType() {
        return repairType;
    }

    public void setRepairType(RepairType repairType) {
        this.repairType = repairType;
    }

    public PropertyOwner getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(PropertyOwner propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getProposedStartDate() {
        return proposedStartDate;
    }

    public void setProposedStartDate(String proposedStartDate) {
        this.proposedStartDate = proposedStartDate;
    }

    public String getProposedEndDate() {
        return proposedEndDate;
    }

    public void setProposedEndDate(String proposedEndDate) {
        this.proposedEndDate = proposedEndDate;
    }

    public BigDecimal getProposedCost() {
        return proposedCost;
    }

    public void setProposedCost(BigDecimal proposedCost) {
        this.proposedCost = proposedCost;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(String actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public String getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(String actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

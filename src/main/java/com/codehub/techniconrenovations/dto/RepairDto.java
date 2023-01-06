package com.codehub.techniconrenovations.dto;

import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import com.codehub.techniconrenovations.model.Property;
import com.codehub.techniconrenovations.model.PropertyOwner;
import com.codehub.techniconrenovations.model.PropertyRepair;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.util.Date;

public class RepairDto {

    @JsonIgnore
    private int repairId;
    @JsonIgnore
    private RepairType repairType;
    @JsonIgnore
    private PropertyOwner propertyOwner;
    @JsonIgnore
    private Property property;
    @JsonSerialize(using = com.codehub.techniconrenovations.serializers.DateSerializer.class)
    @JsonDeserialize(using = com.codehub.techniconrenovations.serializers.DateDeserializer.class)
    private Date date;
    @JsonIgnore
    private String shortDescription;
    @JsonSerialize(using = com.codehub.techniconrenovations.serializers.DateSerializer.class)
    @JsonDeserialize(using = com.codehub.techniconrenovations.serializers.DateDeserializer.class)
    private Date proposedStartDate;
    @JsonSerialize(using = com.codehub.techniconrenovations.serializers.DateSerializer.class)
    @JsonDeserialize(using = com.codehub.techniconrenovations.serializers.DateDeserializer.class)
    private Date proposedEndDate;
    @JsonIgnore
    private BigDecimal proposedCost;
    @JsonIgnore
    private String desciption;
    @JsonIgnore
    private boolean accepted;
    @JsonIgnore
    private Status status;
    @JsonSerialize(using = com.codehub.techniconrenovations.serializers.DateSerializer.class)
    @JsonDeserialize(using = com.codehub.techniconrenovations.serializers.DateDeserializer.class)
    private Date actualStartDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date actualEndDate;
    @JsonIgnore
    private boolean isDeleted;

    public RepairDto(PropertyRepair repair) {
        if (repair != null) {
            repairId = repair.getRepairId();
            propertyOwner = repair.getPropertyOwner();
            property = repair.getProperty();
            repairType = repair.getRepairType();
            date = repair.getDate();
            shortDescription = repair.getShortDescription();
            proposedStartDate = repair.getProposedStartDate();
            proposedEndDate = repair.getProposedEndDate();
            proposedCost = repair.getProposedCost();
            desciption = repair.getDesciption();
            accepted = repair.isAccepted();
            status = repair.getStatus();
            actualStartDate = repair.getActualStartDate();
            actualEndDate = repair.getActualEndDate();
            isDeleted = repair.isIsDeleted();
        }
    }

    public PropertyRepair asRepair() {
        PropertyRepair repair = new PropertyRepair();
        repair.setRepairId(repairId);
        repair.setPropertyOwner(propertyOwner);
        repair.setProperty(property);
        repair.setRepairType(repairType);
        repair.setDate(date);
        repair.setShortDescription(shortDescription);
        repair.setProposedStartDate(proposedStartDate);
        repair.setProposedEndDate(proposedEndDate);
        repair.setProposedCost(proposedCost);
        repair.setDesciption(desciption);
        repair.setAccepted(accepted);
        repair.setStatus(status);
        repair.setActualStartDate(actualStartDate);
        repair.setActualEndDate(actualEndDate);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Date getProposedStartDate() {
        return proposedStartDate;
    }

    public void setProposedStartDate(Date proposedStartDate) {
        this.proposedStartDate = proposedStartDate;
    }

    public Date getProposedEndDate() {
        return proposedEndDate;
    }

    public void setProposedEndDate(Date proposedEndDate) {
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

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

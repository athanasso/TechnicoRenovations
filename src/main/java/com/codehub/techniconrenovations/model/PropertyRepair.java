package com.codehub.techniconrenovations.model;

import com.codehub.techniconrenovations.enums.RepairType;
import com.codehub.techniconrenovations.enums.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Repairs")
public class PropertyRepair extends Object {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int repairId; // Primary key
    @ManyToOne(targetEntity = PropertyOwner.class)
    @JoinColumn(name = "vatNumber", referencedColumnName = "vatNumber")
    private PropertyOwner propertyOwner; // Foreign key
    @ManyToOne(targetEntity = Property.class)
    @JoinColumn(name = "propertyId", referencedColumnName = "propertyId")
    private Property property; // Foreign key    
    @Enumerated(EnumType.STRING)
    private RepairType repairType;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String shortDescription;
    @Temporal(TemporalType.DATE)
    private Date proposedStartDate;
    @Temporal(TemporalType.DATE)
    private Date proposedEndDate;
    private BigDecimal proposedCost;
    private String description;
    private boolean accepted;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Temporal(TemporalType.DATE)
    private Date actualStartDate;
    @Temporal(TemporalType.DATE)
    private Date actualEndDate;
    private boolean isDeleted = false;

    public PropertyRepair(PropertyOwner propertyOwner, Property property, RepairType repairType, Date date, String shortDescription, Date proposedStartDate, Date proposedEndDate, BigDecimal proposedCost, String description, boolean accepted, Status status, Date actualStartDate, Date actualEndDate) {
        this.propertyOwner = propertyOwner;
        this.property = property;
        this.repairType = repairType;
        this.date = date;
        this.shortDescription = shortDescription;
        this.proposedStartDate = proposedStartDate;
        this.proposedEndDate = proposedEndDate;
        this.proposedCost = proposedCost;
        this.description = description;
        this.accepted = accepted;
        this.status = status;
        this.actualStartDate = actualStartDate;
        this.actualEndDate = actualEndDate;
    }


    public PropertyRepair(PropertyOwner propertyOwner, Property property, RepairType repairType, Date date, String shortDescription, String desciption, Status status) {
        this.propertyOwner = propertyOwner;
        this.property = property;
        this.repairType = repairType;
        this.date = date;
        this.shortDescription = shortDescription;
        this.description = desciption;
        this.status = status;
    }

    public void setRepairType(RepairType repairType) {
        this.repairType = repairType;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDesciption(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    

    public PropertyRepair() {

    }

    public int getRepairId() {
        return repairId;
    }

    public void setRepairId(int repairId) {
        this.repairId = repairId;
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

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
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

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public RepairType getRepairType() {
        return repairType;
    }

    public Date getDate() {
        return date;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDesciption() {
        return description;
    }

    public Status getStatus() {
        return status;
    }
}

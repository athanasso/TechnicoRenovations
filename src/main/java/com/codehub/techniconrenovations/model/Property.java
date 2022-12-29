package com.codehub.techniconrenovations.model;

import com.codehub.techniconrenovations.enums.PropertyType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Properties")
public class Property {

    @Id
    private String propertyId;// Primary key
    private String propertyAddress;
    private int yearOfConstruction;
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    @ManyToOne(targetEntity = PropertyOwner.class)
    @JoinColumn(name = "vatNumber", referencedColumnName = "vatNumber")
    private PropertyOwner propertyOwner; // Foreign key   
    private boolean isDeleted = false;

    public Property(String propertyId, String propertyAddress, int yearOfConstruction, PropertyType propertyType, PropertyOwner propertyOwner) {
        this.propertyId = propertyId;
        this.propertyAddress = propertyAddress;
        this.yearOfConstruction = yearOfConstruction;
        this.propertyType = propertyType;
        this.propertyOwner = propertyOwner;
    }

    public void setYearOfConstruction(int yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public Property(String propertyId) {
        this.propertyId = propertyId;
    }

    public Property() {
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public void setPropertyOwner(PropertyOwner propertyOwner) {
        this.propertyOwner = propertyOwner;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public PropertyOwner getPropertyOwner() {
        return propertyOwner;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}

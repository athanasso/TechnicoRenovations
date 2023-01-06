package com.codehub.techniconrenovations.dto;

import com.codehub.techniconrenovations.enums.PropertyType;
import com.codehub.techniconrenovations.model.Property;

public class PropertyDto {
    
    private String propertyId;
    private String propertyAddress;
    private int yearOfConstruction;
    private String propertyType;

    public PropertyDto(Property property) {
        if (property != null) {
            propertyId = property.getPropertyId();
            propertyAddress = property.getPropertyAddress();
            yearOfConstruction = property.getYearOfConstruction();
            propertyType = property.getPropertyType().toString();
        }
    }

    public Property asProperty() {
        Property property = new Property();
        property.setPropertyId(propertyId);
        property.setPropertyAddress(propertyAddress);
        property.setYearOfConstruction(yearOfConstruction);
        property.setPropertyType(PropertyType.valueOf(propertyType));
        return property;
    }

    public PropertyDto() {
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(int yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}

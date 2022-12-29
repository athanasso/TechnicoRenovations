package com.codehub.techniconrenovations.dto;

import com.codehub.techniconrenovations.model.PropertyOwner;

public class PropertyOwnerDto {
    private int id;
    private String name;

    public PropertyOwnerDto() {
    }
    
    public PropertyOwnerDto(PropertyOwner propertyOwner) {
        this.id = propertyOwner.getVatNumber();
        this.name = propertyOwner.getName();
    }
    
    public PropertyOwner asUser() {
        PropertyOwner propertyOwner = new PropertyOwner();
        propertyOwner.setVatNumber(id);
        propertyOwner.setName(name);
        return propertyOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

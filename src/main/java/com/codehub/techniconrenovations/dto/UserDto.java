package com.codehub.techniconrenovations.dto;

import com.codehub.techniconrenovations.model.PropertyOwner;

public class UserDto {

    private int vatNumber;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private String typeOfUser;

    public UserDto(PropertyOwner owner) {
        if (owner != null) {
            vatNumber = owner.getVatNumber();
            name = owner.getName();
            surname = owner.getSurname();
            address = owner.getAddress();
            phoneNumber = owner.getPhoneNumber();
            email = owner.getEmail();
            typeOfUser = owner.getTypeOfUser();
        }
    }
    
    public PropertyOwner asUser() {
        PropertyOwner owner = new PropertyOwner();
        owner.setVatNumber(vatNumber);
        owner.setName(name);
        owner.setSurname(surname);
        owner.setAddress(address);
        owner.setPhoneNumber(phoneNumber);
        owner.setEmail(email);
        owner.setTypeOfUser(typeOfUser);
        return owner;
    }

    public UserDto() {
    }

    public int getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(int vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
}

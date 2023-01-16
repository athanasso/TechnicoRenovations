package com.codehub.techniconrenovations.dto;

import com.codehub.techniconrenovations.model.PropertyOwner;

public class UserDto {

    private int vatNumber;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private String username;
    private String password;
    private String typeOfUser;
    private boolean isDeleted;

    public UserDto(PropertyOwner owner) {
        if (owner != null) {
            vatNumber = owner.getVatNumber();
            name = owner.getName();
            surname = owner.getSurname();
            address = owner.getAddress();
            phoneNumber = owner.getPhoneNumber();
            email = owner.getEmail();
            username = owner.getUsername();
            password = owner.getPassword();
            typeOfUser = owner.getTypeOfUser();
            isDeleted = owner.isIsDeleted();
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
        owner.setUsername(username);
        owner.setPassword(password);
        owner.setTypeOfUser(typeOfUser);
        owner.setIsDeleted(isDeleted);
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

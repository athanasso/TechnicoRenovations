package com.codehub.techniconrenovations.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin {
    
    @Id
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Admin() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

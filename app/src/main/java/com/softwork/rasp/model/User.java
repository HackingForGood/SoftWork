package com.softwork.rasp.model;

import com.softwork.rasp.Constants;

/**
 * Created by Ebenezer on 6/17/2017.
 */

public class User {
    public enum Role{
        Owner,
        Driver
    }
    private String id, name, email;
    private Role role;


    public User(String name, String email, Role role) {
        this(null, name, email, role);
    }

    public User(String id, String name, String email, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

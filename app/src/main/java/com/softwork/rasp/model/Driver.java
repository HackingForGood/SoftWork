package com.softwork.rasp.model;

/**
 * Created by Ebenezer on 6/17/2017.
 */

public class Driver extends User {

    public Driver(String name, String email) {
        super(name, email, Role.Driver);
    }
}

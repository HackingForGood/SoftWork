package com.softwork.rasp.model;

import android.location.Address;

import java.util.Date;

/**
 * Created by Ebenezer on 6/17/2017.
 */

public class Spot {
    private String address, id;
    private boolean open;
    private String description;
    private Date timeIn, timeOut;

    public Spot(String address, boolean open, String description) {
        this.address = address;
        this.open = open;
        this.description = description;
    }

    public Spot(String address, boolean open, String description, Date timeIn, Date timeOut) {
        this.address = address;
        this.open = open;
        this.description = description;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(Date timeIn) {
        this.timeIn = timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Date timeOut) {
        this.timeOut = timeOut;
    }
}

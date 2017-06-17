package com.softwork.rasp.model;

import android.location.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ebenezer on 6/17/2017.
 */

public class Owner extends User {
    private List<Spot> spots;

    public Owner(String name, String email) {
        this(name, email, Role.Owner, new ArrayList<Spot>());
    }

    public Owner(String name, String email, Role role, List<Spot> spots) {
        super(name, email, role);
        this.spots = spots;
    }

    public List<Spot> getSpots() {
        if(spots == null)
            spots = new ArrayList<>();
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
}

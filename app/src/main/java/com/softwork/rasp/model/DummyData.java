package com.softwork.rasp.model;

import android.location.Address;
import android.location.Geocoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Ebenezer on 6/17/2017.
 */

public class DummyData {
    private static Random random = new Random();

    private static String[] names = {"Bernard", "Ebenezer", "Mark", "Dennis", "Godfrey", "Chris", "Daniel",
    "Fixon", "Glen", "PY"};

    private static String[] emails = {"e@test.com", "e2@test.com", "e3@test.com", "e4@test.com",
            "e5@test.com", "e6@test.com", "e7@test.com", "e8@test.com", "e9@test.com"};

    private static String[] addresses = {"60 kirkland st, cambridge",
            "1 beacon st cambridge",
            "55 kirkland street, cambridge",
            "5 beacon street, cambridge",
            "1 irving street, cambridge",
            "5 honey ave, cambridge",
            "2 washington street, cambridge",
            "5 scott street, cambridge",
            "3 holden street, cambridge",
            "3 farrar st, cambridge"};

    public static List<Owner> makeOwners(){
        List<Owner> owners = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            String name = names[random.nextInt(names.length - 1)];
            String address = addresses[random.nextInt(addresses.length) - 1];
            String email = emails[random.nextInt(emails.length - 1)];

            Spot spot = makeSpot(address);
            Owner o = new Owner(name, email);
            o.setSpots(Arrays.asList(spot));
            owners.add(o);
        }

        return owners;
    }

    private static Spot makeSpot(String strAddress){
        boolean open = random.nextBoolean();
        String description = "Dummy spot";
        Date timeIn = new Date(Calendar.getInstance().getTimeInMillis() - random.nextInt(20000));
        Date timeOut = new Date(Calendar.getInstance().getTimeInMillis()+ random.nextInt(20000));

        return new Spot(strAddress, open, description, timeIn, timeIn);
    }
}

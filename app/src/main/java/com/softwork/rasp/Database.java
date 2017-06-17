package com.softwork.rasp;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.softwork.rasp.model.Owner;
import com.softwork.rasp.model.Spot;
import com.softwork.rasp.model.User;

/**
 * Created by Ebenezer on 6/17/2017.
 */

public class Database {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static  DatabaseReference usersRef = database.getReference("users");
    private static  DatabaseReference spotsRef = database.getReference("spots");

    public static String writeUser(User user){
        String key = usersRef.push().getKey();
        user.setId(key);
        usersRef.child(key).setValue(user);

        if(user instanceof Owner){
            for(Spot s : ((Owner) user).getSpots())
                writeSpot(s);
        }

        return key;
    }

    public static String writeSpot(Spot s){
        String key = spotsRef.push().getKey();
        s.setId(key);
        spotsRef.child(key).setValue(s);

        return key;
    }

    public static void readSpots(ChildEventListener listener, ValueEventListener completionListener){
        spotsRef.addChildEventListener(listener);
        spotsRef.addListenerForSingleValueEvent(completionListener);
    }

}

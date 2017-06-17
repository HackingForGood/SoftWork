package com.softwork.rasp;

import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.softwork.rasp.model.Spot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static String TAG = "MAP";
    private GoogleMap mMap;
    private LatLng currentLocation;
    private SupportMapFragment mapFragment;
    private List<Spot> spots, matches;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
         mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setup();
    }

    private void setup(){
        spots = new ArrayList<>();
        matches = new ArrayList<>();

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                currentLocation = place.getLatLng();
                findSpots();
                mapFragment.getMapAsync(MapsActivity.this);
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(currentLocation == null)
            return;

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        mMap.animateCamera(zoom);
    }

    private void findSpots(){
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Spot spot = dataSnapshot.getValue(Spot.class);
                addSpot(spot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ValueEventListener completionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(MapsActivity.this, String.format("%d spots found", spots.size()),
                        Toast.LENGTH_SHORT);
                searchComplete();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        Database.readSpots(listener, completionListener);
    }

    private void addSpot(Spot s){
        if(!spots.contains(s))
            spots.add(s);
    }

    private void searchComplete(){
        for(Spot s : spots){
            if(getLocationFromAddress(getAddresses(s.getAddress())).equals(currentLocation))
                matches.add(s);
        }
        buildAddressDialog();
        dialog.show();
    }


    private void buildAddressDialog() {
        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, matches);

        dialog = builder.create();
    }



    private Address getAddresses(String strAddress) {
        Geocoder coder = new Geocoder(this);

        try {
            // May throw an IOException
            return coder.getFromLocationName(strAddress, 10).get(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public LatLng getLocationFromAddress(Address location) {
        location.getLatitude();
        location.getLongitude();

        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}

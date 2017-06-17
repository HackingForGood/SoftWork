package com.softwork.rasp;


import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OwnerRegistrationFragment extends Fragment implements OnMapReadyCallback {

    private EditText addressEditText;
    private SupportMapFragment mapFragment;
    private AlertDialog dialog;
    private Address address;

    public OwnerRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_owner_registration, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setup();
    }

    private void setup() {
        //set up map listener for addressEditText
        addressEditText = (EditText) getView().findViewById(R.id.addressEditText);
        addressEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || event == null
                        || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    getAddress();
                    return true;
                }
                return false;
            }
        });

        //set up number picker
        NumberPicker picker = (NumberPicker) getView().findViewById(R.id.numberPicker);
        picker.setMinValue(0);
        picker.setMaxValue(10);

    }

    private void loadMap() {
        getAddress();
        mapFragment.getMapAsync(this);
    }

    private void getAddress() {
        buildAddressDialog();

        dialog.show();
    }

    private void buildAddressDialog() {
        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
        final List<Address> addresses = getAddresses(addressEditText.getText().toString());

        ArrayAdapter<Address> adapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_2, android.R.id.text1, addresses){

            @Override
            public View getView(int position,
                                View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(addresses.get(position).getAddressLine(0));

                text2.setText( addresses.get(position).getAddressLine(1) );

                return view;
            }

        };

        builder.setTitle(R.string.pick_address)
                .setSingleChoiceItems(adapter, 0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                address = addresses.get(which);
                            }
                        });

        dialog = builder.create();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Do nothing if no addressEditText
        if (address == null)
            return;
        LatLng ad = getLocationFromAddress(address);
        googleMap.addMarker(new MarkerOptions().position(ad)
                .title(address.getAddressLine(0)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ad));
    }

    private List<Address> getAddresses(String strAddress) {
        Geocoder coder = new Geocoder(getContext());

        try {
            // May throw an IOException
            return coder.getFromLocationName(strAddress, 10);
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

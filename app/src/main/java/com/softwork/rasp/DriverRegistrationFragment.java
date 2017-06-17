package com.softwork.rasp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softwork.rasp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DriverRegistrationFragment extends Fragment {


    public DriverRegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_registration, container, false);
    }

}

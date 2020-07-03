package com.example.linguine.Fragments;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.linguine.Common.SessionMangment;
import com.example.linguine.Models.OrderModel;
import com.example.linguine.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import im.delight.android.location.SimpleLocation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeOrderFragment extends Fragment implements View.OnClickListener {
    View view;
    static List<Address> addresses = null;
    SimpleLocation location;
    static double latitude;
    static double longitude;

    Button mGetLocation, mMakeOrder;

    EditText mLocation, mPhone, mOrder;

    DatabaseReference mDatabaseReference, mOrderReference;

    static String address, city, state, country, postalCode, knownName;

    SessionMangment mSessionMangment;

    OrderModel mOrderModel;

    public MakeOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_make_order, container, false);
        location = new SimpleLocation(Objects.requireNonNull(getActivity()));
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("User Data").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mOrderReference = FirebaseDatabase.getInstance().getReference("Orders");
        mSessionMangment = new SessionMangment(getActivity());

        InstViews();

        return view;
    }

    private void InstViews() {
        mLocation = view.findViewById(R.id.mMyLocation);
        mPhone = view.findViewById(R.id.mMyPhone);
        mOrder = view.findViewById(R.id.mOrder);


        mPhone.setText(mSessionMangment.getUserDetails().get(SessionMangment.KEY_PHONE));

        mGetLocation = view.findViewById(R.id.mGetLocation);
        mGetLocation.setOnClickListener(this);

        mMakeOrder = view.findViewById(R.id.mMakeOrder);
        mMakeOrder.setOnClickListener(this);

    }


    void ConvertAddress() {
        Geocoder geocoder;

        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = addresses.get(0).getLocality();
            state = addresses.get(0).getAdminArea();
            country = addresses.get(0).getCountryName();
            postalCode = addresses.get(0).getPostalCode();
            knownName = addresses.get(0).getFeatureName();


        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    //----------------------------------------------------------------------------------------------
    void GetAddress() {


        // if we can't access the location yet
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(getActivity());
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mGetLocation:
                GetAddress();
                ConvertAddress();
                mLocation.setText(city + " " + state + " " + country + " " + knownName);
                UpdateLocation();
                break;
            case R.id.mMakeOrder:
                if (validate()) {
                    MakeOrder();
                }
                break;

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        // make the device update its location
        location.beginUpdates();

        // ...
    }

    @Override
    public void onPause() {
        // stop location updates (saves battery)
        location.endUpdates();

        // ...

        super.onPause();
    }

    void UpdateLocation() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("mLat", String.valueOf(latitude));
        map.put("mLong", String.valueOf(longitude));
        mDatabaseReference.updateChildren(map);


        mSessionMangment.updateLocation(String.valueOf(latitude), String.valueOf(longitude));


    }

    void MakeOrder() {
        mOrderModel = new OrderModel(mSessionMangment.getUserDetails().get(mSessionMangment.KEY_PHONE),
                String.valueOf(latitude),
                String.valueOf(longitude),
                mLocation.getText().toString(),
                mOrder.getText().toString());

        mOrderReference.child(mSessionMangment.getUserDetails().get(mSessionMangment.KEY_PHONE)).setValue(mOrderModel);

        mOrder.setText("");

        Snackbar.make(getActivity().findViewById(android.R.id.content), "We are received Your order", Snackbar.LENGTH_LONG).show();

    }

    private boolean validate() {


        if (mLocation.getText().toString().isEmpty()) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Please enter your Address", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (mPhone.getText().toString().isEmpty()) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Please enter your phone", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (mOrder.getText().toString().isEmpty()) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Please enter your Order", Snackbar.LENGTH_LONG).show();
            return false;
        } else {

            return true;

        }
    }
}

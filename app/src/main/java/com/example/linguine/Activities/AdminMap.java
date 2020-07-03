package com.example.linguine.Activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.linguine.Models.OrderModel;
import com.example.linguine.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Intent mIntent;
    DatabaseReference mDatabaseReference;
     String mLat, mLong, mOrder , mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mIntent = getIntent();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Orders");

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

    void getLatLong() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mDatabaseReference.child(mIntent.getStringExtra("mPhone")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                OrderModel model = snapshot.getValue(OrderModel.class);
                mLat = model.getmLat();
                mLong = model.getmLong();
                mOrder = model.getmOrder();
                mPhone = model.getmPhone();
                LatLng sydney = new LatLng(Double.parseDouble(mLat), Double.parseDouble(mLong));
                mMap.addMarker(new MarkerOptions().position(sydney).title(mOrder + " , "+ mPhone));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(sydney,20));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//         Add a marker in Sydney and move the camera

    }


}

package com.google.jsteam.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.jsteam.R;

public class MapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        if(view == null){
            return null;
        }
        SupportMapFragment sMapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapMarker);
        sMapFrag.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.getUiSettings().setCompassEnabled(true);
                googleMap.getUiSettings().setZoomGesturesEnabled(true);
                googleMap.getUiSettings().setZoomControlsEnabled(true);


                LatLng loc = new LatLng(-6.201733,106.781592);
                MarkerOptions marker = new MarkerOptions();
                marker.position(loc);
                marker.title("JSteam's Headquarter in Jakarta");
                googleMap.clear();
                googleMap.addMarker(marker);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            }
        });
        return view;
    }
}
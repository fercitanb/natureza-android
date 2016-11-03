package com.mariafernandanb.natureza.option;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mariafernandanb.natureza.R;
import com.mariafernandanb.natureza.registroDireccion;

/**
 * Created by gonzalopro on 11/2/16.
 */

public class Direcciones extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opt_direccion, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(Direcciones.this);


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cbba = new LatLng(-17.392627, -66.158730);

        LatLng work = new LatLng(-17.368800, -66.163163);
        LatLng restaurant = new LatLng(-17.373494, -66.143598);
        LatLng place = new LatLng(-17.392627, -66.158730);

        mMap.addMarker(new MarkerOptions().position(place).title("Mi Trabajo").snippet("asdsad").zIndex(5));
        mMap.addMarker(new MarkerOptions().position(work).title("Mi Casa").snippet("asdsad").zIndex(3));
        mMap.addMarker(new MarkerOptions().position(restaurant).title("Mi Oficina").snippet("asdsad").zIndex(1));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cbba,12));
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        int id = (int) marker.getZIndex();
        Log.d("Dir", "Cast index: " + id);

        startActivity(new Intent(getActivity(), registroDireccion.class).putExtra("idDireccion", id));

        return false;
    }
}

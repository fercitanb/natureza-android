package com.mariafernandanb.natureza.option;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mariafernandanb.natureza.Init;
import com.mariafernandanb.natureza.R;
import com.mariafernandanb.natureza.registroDireccion;
import com.mariafernandanb.natureza.util.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        String idUsuario = ((Init) getActivity().getApplicationContext()).getIdUsuario();
        mMap = googleMap;
        LatLng cbba = new LatLng(-17.392627, -66.158730);

        new RecuperarDireccion(Direcciones.this,mMap,idUsuario).execute();

        /*LatLng work = new LatLng(-17.368800, -66.163163);
        LatLng restaurant = new LatLng(-17.373494, -66.143598);
        LatLng place = new LatLng(-17.392627, -66.158730);

        mMap.addMarker(new MarkerOptions().position(place).title("Mi Trabajo").snippet("asdsad").zIndex(5));
        mMap.addMarker(new MarkerOptions().position(work).title("Mi Casa").snippet("asdsad").zIndex(3));
        mMap.addMarker(new MarkerOptions().position(restaurant).title("Mi Oficina").snippet("asdsad").zIndex(1));*/

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


    private class RecuperarDireccion extends AsyncTask<Void,Void,Void>{

        private Direcciones direcciones;
        private GoogleMap mMap;
        private String idUsuario;
        private HttpURLConnection httpURLConnection;
        private StringBuilder result;
        private URL url;

        public RecuperarDireccion(Direcciones direcciones, GoogleMap mMap, String idUsuario) {
            this.direcciones = direcciones;
            this.mMap = mMap;
            this.idUsuario = idUsuario;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(Constantes._URL_DIRECCION);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(Constantes.READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(Constantes.CONNECTION_TIMEOUT);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("cliente", idUsuario);

                String request = builder.build().getEncodedQuery();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(request);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int response_code = httpURLConnection.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                    result = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }

                } else {
                    Log.d("Asy", "Error de conexion");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            try {
                JSONObject group_info = new JSONObject(String.valueOf(result));

                JSONArray code = group_info.getJSONArray("direcciones");

                for (int i = 0; i < code.length() ; i++) {
                    JSONObject jsonGroup = code.getJSONObject(i);


                    mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Double.parseDouble(jsonGroup.getString("")),Double.parseDouble(jsonGroup.getString(""))))
                            .title("Mi ubicación")
                            .snippet(jsonGroup.getString("nombreDireccion"))
                            .zIndex(Float.parseFloat(jsonGroup.getString("idDireccion"))));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

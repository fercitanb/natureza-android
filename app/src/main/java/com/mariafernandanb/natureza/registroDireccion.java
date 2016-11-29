package com.mariafernandanb.natureza;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.location.LocationListener;
import android.location.LocationListener;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.List;
import java.util.Locale;

public class registroDireccion extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;
    private EditText editTextDir;
    private Button buttonGuardarDireccion;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_direccion);
        editTextDir = (EditText) findViewById(R.id.et_registro_direccion_nombre);
        buttonGuardarDireccion = (Button) findViewById(R.id.b_registro_direccion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 18);
        if (marcador != null) marcador.remove();
        marcador = mMap.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title("Mi direccion")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.markr))
        );
        mMap.animateCamera(miUbicacion);
    }


    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);

            String direccion = null;
            Geocoder geocoder = new Geocoder(registroDireccion.this, Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = geocoder.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0)

                    for (Address address: addresses) {
                        System.out.println(address);
                    }

                 direccion = addresses.get(0).getAddressLine(0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String s = "Mi direccion es: " + direccion;
            Log.d("regDir", "dir: " + s);
            editTextDir.setText(direccion);
        }
    }


    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private void miUbicacion() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 0, locListener);
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
        mMap = googleMap;
        miUbicacion();

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("registroDireccion Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());

        buttonGuardarDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUsuario = ((Init) getApplicationContext()).getIdUsuario();
                String direccion = editTextDir.getText().toString();
                String latitud = String.valueOf(lat);
                String longitud = String.valueOf(lng);
                Log.d("RegDir", "Lat: " +latitud+ " Lon: " +longitud+ " Dir: " + direccion);

                new NuevaDireccion(registroDireccion.this,idUsuario,direccion,latitud,longitud).execute();

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    private class NuevaDireccion extends AsyncTask<Void,Void,Void>{

        private registroDireccion registroDireccion;
        private String idUsuario;
        private String direccion;
        private String latitud;
        private String longitud;
        private HttpURLConnection httpURLConnection;
        private StringBuilder result;
        private URL url;

        public NuevaDireccion(com.mariafernandanb.natureza.registroDireccion registroDireccion, String idUsuario, String direccion, String latitud, String longitud) {
            this.registroDireccion = registroDireccion;
            this.idUsuario = idUsuario;
            this.direccion = direccion;
            this.latitud = latitud;
            this.longitud = longitud;
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
                        .appendQueryParameter("txtDireccion", direccion)
                        .appendQueryParameter("txtLongitud", longitud)
                        .appendQueryParameter("txtLatitud", latitud)
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

                JSONArray code = group_info.getJSONArray("code");

                for (int i = 0; i < code.length() ; i++) {
                    int response = code.getInt(i);

                    switch (response){
                        case 0:
                            Log.d("Session", "codeError: " + response);
                            Toast.makeText(registroDireccion,"Error al intentar insertar direccion",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(getApplicationContext(),SessionDristribuidor.class));
                            break;
                        case 1:
                            Log.d("Session", "codeError: " + response);
                            registroDireccion.onBackPressed();
                            //startActivity(new Intent(getApplicationContext(),SessionCliente.class));
                            break;
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

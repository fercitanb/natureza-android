package com.mariafernandanb.natureza;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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

public class Registrar extends AppCompatActivity {

    private AutoCompleteTextView mciv, mnombrev, mappatv, mapmat, mnumv, memailv;
    private Button mregistrarv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        mciv = (AutoCompleteTextView) findViewById(R.id.ci);
        mnombrev = (AutoCompleteTextView) findViewById(R.id.nombre);
        mappatv = (AutoCompleteTextView) findViewById(R.id.apPat);
        mapmat = (AutoCompleteTextView) findViewById(R.id.apMat);
        mnumv = (AutoCompleteTextView) findViewById(R.id.num);
        memailv = (AutoCompleteTextView) findViewById(R.id.correo);
        mregistrarv = (Button) findViewById(R.id.registrar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mregistrarv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ci = mciv.getText().toString();
                String nom = mnombrev.getText().toString();
                String ap = mappatv.getText().toString();
                String am = mapmat.getText().toString();
                String num = mnumv.getText().toString();
                String corr = memailv.getText().toString();

                new RegistroUsuario(ci,nom,ap,am,num,corr).execute();
            }
        });

    }

    public class RegistroUsuario extends AsyncTask<Void, Void, Void> {

        HttpURLConnection httpURLConnection;
        StringBuilder result;
        URL url;
        private final String mCi;
        private final String mNombre;
        private final String mApPat;
        private final String mApMat;
        private final String mNum;
        private final String mCorreo;

        RegistroUsuario(String mCi, String mNombre, String mApPat, String mApMat, String mNum, String mCorreo) {
            this.mNum = mNum;
            this.mCorreo = mCorreo;
            this.mCi = mCi;
            this.mNombre = mNombre;
            this.mApPat = mApPat;
            this.mApMat = mApMat;

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: register the new account here.
            try {
                url = new URL(Constantes._URL_REGISTRO);
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
                        .appendQueryParameter("ci", mCi).appendQueryParameter("nombre", mNombre).appendQueryParameter("apellidoPaterno", mApPat).appendQueryParameter("apellidoMaterno", mApMat).appendQueryParameter("numeroTelefono", mNum).appendQueryParameter("email", mCorreo);

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

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void success) {


            try {
                JSONObject group_info = new JSONObject(String.valueOf(result));

                JSONArray code = group_info.getJSONArray("code");

                for (int i = 0; i < code.length() ; i++) {
                    int response = code.getInt(i);


                    switch (response){
                        case 0:
                            Log.d("Registro","CLIENTE EXISTENTE: " + response);
                            break;
                        case 1:
                            Log.d("Registro", "REGISTO EXITOSO: " + response);
                            break;
                        case 2:
                            Log.d("Registro", "ERROR AL ENVIAR CORREO COMUNICARSE CON LA EMPRESA: " + response);
                            break;
                    }


                    Log.d("Response", "value: " + response);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            /*if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

    }

}

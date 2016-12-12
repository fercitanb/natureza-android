package com.mariafernandanb.natureza;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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

import static com.mariafernandanb.natureza.util.Constantes.CONNECTION_TIMEOUT;
import static com.mariafernandanb.natureza.util.Constantes.READ_TIMEOUT;
import static com.mariafernandanb.natureza.util.Constantes._URL_ACEPTAR_PEDIDO;

/**
 * Created by gonzalopro on 12/12/16.
 */

public class AceptarPedido extends AsyncTask<Void,Void,Void> {

    private String idPedido;
    private String monto;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private StringBuilder result;

    public AceptarPedido(String idPedido, String monto) {
        this.idPedido = idPedido;
        this.monto = monto;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            url = new URL(_URL_ACEPTAR_PEDIDO);
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("idPedido", idPedido)
                    .appendQueryParameter("monto", monto);

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
                    Log.d("Async", "result: " + result);
                }


            } else {
                Log.d("Async", "error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return null;
    }
}

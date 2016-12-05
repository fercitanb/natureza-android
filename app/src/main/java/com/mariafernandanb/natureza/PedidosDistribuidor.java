package com.mariafernandanb.natureza;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.List;

import static com.mariafernandanb.natureza.util.Constantes.CONNECTION_TIMEOUT;
import static com.mariafernandanb.natureza.util.Constantes.READ_TIMEOUT;
import static com.mariafernandanb.natureza.util.Constantes._URL_OBTENER_DETALLE_PEDIDO;

/**
 * Created by gonzalopro on 11/29/16.
 */

public class PedidosDistribuidor extends AppCompatActivity {

    public static final String TAG = PedidosDistribuidor.class.getSimpleName();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager linearLayout;
    private TextView tvTotal;

    private String idPedido;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_distribuidor);

        idPedido = getIntent().getStringExtra("idPedido");
        Log.d(TAG, "idPedido: " + idPedido);
        tvTotal = (TextView) findViewById(R.id.tv_activity_pedidos);
        recyclerView = (RecyclerView) findViewById(R.id.rv_pedido_distribuidor);
        recyclerView.setHasFixedSize(true);
        linearLayout = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayout);
        new GetOrderByDistributor(idPedido).execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private class GetOrderByDistributor extends AsyncTask<Void,Void,Void> {

        private List<PedidoModelo> pedidoModelos;
        private String idPedido;

        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultPedido;

        public GetOrderByDistributor(String idPedido) {
            this.idPedido = idPedido;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(_URL_OBTENER_DETALLE_PEDIDO);
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
                        .appendQueryParameter("idPedido", idPedido);

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

                    resultPedido = new StringBuilder();
                    String line;

                    while ((line = bufferedReader.readLine()) != null) {
                        resultPedido.append(line);
                        System.out.println("Pedidos: " +resultPedido);
                    }


                } else {
                    Log.d("Async", "error async");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                httpURLConnection.disconnect();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pedidoModelos = new ArrayList<>();
            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultPedido));

                JSONArray jsonArray = group_info.getJSONArray("detallePedido");
                JSONArray jsonArrayTotal = group_info.getJSONArray("total");
                String total = jsonArrayTotal.getString(0);
                tvTotal.setText("TOTAL: " + total + " Bs.");
                for (int i = 0; i < jsonArray.length() ; i++) {

                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    pedidoModelos.add(i, new PedidoModelo(jsonGroup.getString("idProducto"),jsonGroup.getString("idPedido"),jsonGroup.getString("cantidad"),jsonGroup.getString("precio"),jsonGroup.getString("nombre"),jsonGroup.getString("medida"),jsonGroup.getString("imagen")));

                    recyclerView.setAdapter(new PDAdapter(pedidoModelos));


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            recyclerView.setAdapter(new PDAdapter(pedidoModelos));
        }
    }

    private class PDAdapter extends RecyclerView.Adapter<PDAdapter.PDHolder> {

        private List<PedidoModelo> pedidoModelos;

        public PDAdapter(List<PedidoModelo> pedidoModelos) {
            this.pedidoModelos = pedidoModelos;
        }

        @Override
        public PDAdapter.PDHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_pedido, parent, false);
            return new PDHolder(view);
        }

        @Override
        public void onBindViewHolder(PDAdapter.PDHolder holder, int position) {
            PedidoModelo pedidoModelo = pedidoModelos.get(position);

            holder.tvNombre.setText(pedidoModelo.getNombre());
            holder.tvMedida.setText(pedidoModelo.getMedida());
            holder.tvCantidad.setText(pedidoModelo.getCantidad());
            holder.tvPrecio.setText(pedidoModelo.getPrecio() + " Bs.");
        }

        @Override
        public int getItemCount() {
            return pedidoModelos.size();
        }

        public class PDHolder extends RecyclerView.ViewHolder {

            ImageView imageViewLogo;
            TextView tvNombre, tvMedida, tvCantidad, tvPrecio;

            public PDHolder(View itemView) {
                super(itemView);

                imageViewLogo = (ImageView) itemView.findViewById(R.id.iv_cell_pedido_logo);
                tvNombre = (TextView) itemView.findViewById(R.id.tv_cell_pedido_nombre);
                tvMedida = (TextView) itemView.findViewById(R.id.tv_cell_pedido_medida);
                tvCantidad = (TextView) itemView.findViewById(R.id.tv_cell_pedido_cantidad);
                tvPrecio = (TextView) itemView.findViewById(R.id.tv_cell_pedido_precio);
            }
        }
    }


}
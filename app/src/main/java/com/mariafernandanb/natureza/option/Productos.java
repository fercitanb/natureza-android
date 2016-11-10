package com.mariafernandanb.natureza.option;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mariafernandanb.natureza.ProductosModelo;
import com.mariafernandanb.natureza.R;
import com.mariafernandanb.natureza.util.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gonzalopro on 11/9/16.
 */

public class Productos extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opt_producto, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_item);
        recyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        new ItemTask().execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private List<ProductosModelo> productosModelos;

    private class ItemTask extends AsyncTask<Void,Void,Void>{

        private URL url;
        private HttpURLConnection httpURLConnection;
        private StringBuilder resultItems;

        @Override
        protected Void doInBackground(Void... params) {

            try {
                url = new URL(Constantes._URL_OBTENER_PRODUCTOS);
            } catch (MalformedURLException e){
                e.printStackTrace();
            }

            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(Constantes.READ_TIMEOUT);
                httpURLConnection.setConnectTimeout(Constantes.CONNECTION_TIMEOUT);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                int response_code = httpURLConnection.getResponseCode();

                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    resultItems = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        resultItems.append(line);
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

            productosModelos = new ArrayList<>();
            Log.d("Async", "result: " + resultItems);

            try {
                JSONObject group_info = new JSONObject(String.valueOf(resultItems));

                JSONArray jsonArray = group_info.getJSONArray("productos");

                for (int i = 0; i < jsonArray.length() ; i++) {
                    JSONObject jsonGroup = jsonArray.getJSONObject(i);
                    productosModelos.add(i, new ProductosModelo(jsonGroup.getString("idProducto"),jsonGroup.getString("nombre"),jsonGroup.getString("medida"),jsonGroup.getString("precio"),jsonGroup.getString("imagen")));
                    recyclerView.setAdapter(new ItemAdapter(productosModelos));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CellItem> {

        private List<ProductosModelo> productosModelos;

        public ItemAdapter(List<ProductosModelo> productosModelos) {
            this.productosModelos = productosModelos;
        }

        @Override
        public CellItem onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item, parent, false);
            return new CellItem(view);
        }

        @Override
        public void onBindViewHolder(CellItem holder, int position) {
            ProductosModelo productosModelo = productosModelos.get(position);
            holder.textViewTitle.setText(productosModelo.getNombre());
            holder.textViewPrice.setText(productosModelo.getPrecio());
            holder.textViewContent.setText(productosModelo.getMedida());
        }

        @Override
        public int getItemCount() {
            return productosModelos.size();
        }

        public class CellItem extends RecyclerView.ViewHolder {

            private TextView textViewTitle;
            private TextView textViewPrice;
            private TextView textViewContent;

            public CellItem(View itemView) {
                super(itemView);

                textViewTitle = (TextView) itemView.findViewById(R.id.tv_cell_item_title);
                textViewPrice = (TextView) itemView.findViewById(R.id.tv_cell_item_precio);
                textViewContent = (TextView) itemView.findViewById(R.id.tv_cell_item_detail);

            }
        }
    }
}

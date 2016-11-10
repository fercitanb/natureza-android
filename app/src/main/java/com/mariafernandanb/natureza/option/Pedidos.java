package com.mariafernandanb.natureza.option;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mariafernandanb.natureza.R;

/**
 * Created by gonzalopro on 11/9/16.
 */

public class Pedidos extends Fragment {

    private RecyclerView recyclerViewItem;
    private RecyclerView recyclerViewDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opt_pedido, container, false);

        recyclerViewItem = (RecyclerView) view.findViewById(R.id.rv_pedido_item);
        recyclerViewDevice = (RecyclerView) view.findViewById(R.id.rv_pedido_device);

        recyclerViewItem.setHasFixedSize(true);
        recyclerViewDevice.setHasFixedSize(true);

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerViewDevice.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));


        recyclerViewItem.setAdapter(new ItemAdapter());
        recyclerViewItem.setAdapter(new DeviceAdapter());


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CellOrder> {

        @Override
        public CellOrder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_order, parent, false);
            return new CellOrder(view);
        }

        @Override
        public void onBindViewHolder(CellOrder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class CellOrder extends RecyclerView.ViewHolder {

            public CellOrder(View itemView) {
                super(itemView);
            }
        }
    }

    private class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.CellOrder> {

        @Override
        public CellOrder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_order, parent, false);
            return new CellOrder(view);
        }

        @Override
        public void onBindViewHolder(CellOrder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class CellOrder extends RecyclerView.ViewHolder {
            public CellOrder(View itemView) {
                super(itemView);
            }
        }
    }
}

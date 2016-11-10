package com.mariafernandanb.natureza.option;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mariafernandanb.natureza.R;

/**
 * Created by gonzalopro on 11/9/16.
 */

public class Equipos extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.opt_equipo, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

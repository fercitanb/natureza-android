package com.mariafernandanb.natureza;

import android.app.Application;

public class Init extends Application {
    String idUsuario;
    String nombreUsuario;


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}

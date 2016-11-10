package com.mariafernandanb.natureza;

/**
 * Created by gonzalopro on 11/10/16.
 */

public class EquiposModelo {

    String idEquipo;
    String nombre;
    String descripcion;
    String codido;
    String imagen;

    public EquiposModelo(String idEquipo, String nombre, String descripcion, String codido, String imagen) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codido = codido;
        this.imagen = imagen;
    }

    public String getIdEquipo() {
        return idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCodido() {
        return codido;
    }

    public String getImagen() {
        return imagen;
    }
}

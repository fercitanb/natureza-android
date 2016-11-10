package com.mariafernandanb.natureza;

/**
 * Created by gonzalopro on 11/10/16.
 */

public class ProductosModelo {

    String idProducto;
    String nombre;
    String medida;
    String precio;
    String imagen;

    public ProductosModelo(String idProducto, String nombre, String medida, String precio, String imagen) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.medida = medida;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMedida() {
        return medida;
    }

    public String getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }
}

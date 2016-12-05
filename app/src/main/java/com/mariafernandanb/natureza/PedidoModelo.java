package com.mariafernandanb.natureza;

/**
 * Created by gonzalopro on 11/30/16.
 */

public class PedidoModelo {

    private String idProducto;
    private String idPedido;
    private String cantidad;
    private String precio;
    private String nombre;
    private String medida;
    private String imagen;

    public PedidoModelo(String idProducto, String idPedido, String cantidad, String precio, String nombre, String medida, String imagen) {
        this.idProducto = idProducto;
        this.idPedido = idPedido;
        this.cantidad = cantidad;
        this.precio = precio;
        this.nombre = nombre;
        this.medida = medida;
        this.imagen = imagen;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMedida() {
        return medida;
    }

    public String getImagen() {
        return imagen;
    }
}

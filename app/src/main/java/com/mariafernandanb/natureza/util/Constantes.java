package com.mariafernandanb.natureza.util;

/**
 * Created by HUGO on 09/10/2016.
 */
public class Constantes {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    // URL from Services
    //public static final
    static String ip = "10.0.0.11";
    public static final String _URL_AUTENTIFICACION = "http://"+ip+":90/natureza/WebServices/autentificacion.php";
    public static final String _URL_REGISTRO = "http://"+ip+":90/natureza/WebServices/registroCliente.php";
    public static final String _URL_DIRECCION = "http://"+ip+":90/natureza/WebServices/registroDireccion.php";
    public static final String _URL_OBTENER_DIRECCION = "http://"+ip+":90/natureza/WebServices/recuperaDirecciones.php";


}

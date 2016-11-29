package com.mariafernandanb.natureza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class pedir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir);
        String idUsuario = ((Init) getApplicationContext()).getIdUsuario();
        //String url="http://natureza.nextbooks.xyz/Vistas/index.php#ajax/detallePedido.php?id="+idUsuario;
        String url="http://natureza.nextbooks.xyz/Vistas/index2.php#ajax/detallePedido.php?id="+idUsuario;
        WebView view= (WebView) this.findViewById(R.id.idPedir);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        view.setWebViewClient(new WebViewClient());
    }
}

package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mariafernandanb.natureza.Init;
import com.mariafernandanb.natureza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class opt_pedir extends Fragment {


    public opt_pedir() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vw = inflater.inflate(R.layout.fragment_opt_pedir, container, false);
        String idUsuario = ((Init) getContext().getApplicationContext()).getIdUsuario();
        //String url="http://natureza.nextbooks.xyz/Vistas/index.php#ajax/detallePedido.php?id="+idUsuario;
        String url="http://natureza.nextbooks.xyz/Vistas/index2.php#ajax/detallePedido.php?id="+idUsuario;
        WebView view= (WebView) vw.findViewById(R.id.idPedir);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
        view.setWebViewClient(new WebViewClient());

        return vw;
    }

}

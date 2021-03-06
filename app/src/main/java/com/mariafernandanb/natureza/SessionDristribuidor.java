package com.mariafernandanb.natureza;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mariafernandanb.natureza.option.Direcciones;
import com.mariafernandanb.natureza.option.DireccionesPedidos;
import com.mariafernandanb.natureza.option.Equipos;
import com.mariafernandanb.natureza.option.Productos;

import layout.opt_pedir;

public class SessionDristribuidor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean viewIsAtHome;
    TextView tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_dristribuidor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        displayFragment(R.id.nav_dir);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View vw = navigationView.getHeaderView(0);
        tvNombre = (TextView)vw.findViewById(R.id.nombreDistribuidor);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            displayFragment(R.id.nav_dir); //display the News fragment
        } else {
            moveTaskToBack(true);  //If view is in News fragment, exit application
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.session_dristribuidor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       /* int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;*/

        int id = item.getItemId();

        displayFragment(id);
        return true;
    }


    protected void onStart() {
        super.onStart();
        String nombre = ((Init)getApplicationContext()).getNombreUsuario();
        Log.d("Session", " nombre: "+ nombre);
        tvNombre.setText(nombre);

    }
    public void displayFragment(int fragmentId) {

        Fragment fragment = null;
        String titleBar = getString(R.string.app_name);

        switch (fragmentId) {

            case R.id.nav_dir:
                fragment = new DireccionesPedidos();
                titleBar = getString(R.string.nav_dist_address);
                viewIsAtHome = true;
                break;

            /*case R.id.nav_item:
                fragment = new Productos();
                titleBar = getString(R.string.nav_client_item);
                viewIsAtHome = false;
                break;

            case R.id.nav_device:
                fragment = new Equipos();
                titleBar = getString(R.string.nav_client_device);
                viewIsAtHome = false;
                break;

           /* case R.id.nav_order:
                fragment = new Pedidos();
                titleBar = getString(R.string.nav_client_order);
                viewIsAtHome = false;
                break;
            case R.id.nav_order:
                fragment = new opt_pedir();
                titleBar = getString(R.string.nav_client_order);
                viewIsAtHome = false;
                break;*/
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame2, fragment);
            fragmentTransaction.commit();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleBar);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }
}

package pes.agorapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import pes.agorapp.JSONObjects.UserAgorApp;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private PreferencesAgorApp prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        prefs = new PreferencesAgorApp(MainActivity.this);

        /*informació d'usuari*/
        //printProfile();

        //botó logout
        findViewById(R.id.btn_logout).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

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
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void printProfile() {
        setTextProva();
        setImageProva();
    }

    private void setImageProva() {
        /*imatge*/
        if (!prefs.getImageUrl().equals("www.imatgedummy.com")) {
            Picasso.with(getApplicationContext())
                    .load(prefs.getImageUrl())
                    .resize(180, 180)
                    .into((ImageView) findViewById(R.id.img_profile_user));
        } else {
            Picasso.with(getApplicationContext())
                    .load(R.drawable.avatar_face_1_)
                    .resize(180, 180)
                    .into((ImageView) findViewById(R.id.img_profile_user));
        }
    }

    private void setTextProva() {
        String userName = prefs.getUserName();
        String email = prefs.getEmail();
        String image_url = prefs.getImageUrl();
        String platform = prefs.getPlatform();
        String token = prefs.getActiveToken();
        TextView myAwesomeTextView = (TextView) findViewById(R.id.text_prova);
        myAwesomeTextView.setText("LOGUEJAT CORRECTAMENT\n\nNom real: " + userName +
                "\nuserName: " + email +
                "\nimage_url: " + image_url +
                "\nplatform: " + platform +
                "\nactive_token: " + token);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            printProfile();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            printProfile();
        } else if (id == R.id.nav_slideshow) {
            printProfile();
        } else if (id == R.id.nav_manage) {
            printProfile();
        } else if (id == R.id.nav_share) {
            printProfile();
        } else if (id == R.id.nav_send) {
            printProfile();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.btn_logout:
                close_session();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    private void close_session() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Tancar sessió")
                .setMessage("N'estàs segur?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        JsonObject jsonUser = new JsonObject();
                        jsonUser.addProperty("id", prefs.getId());
                        jsonUser.addProperty("active_token", prefs.getActiveToken());

                        AgorAppApiManager
                                .getService()
                                .logoutUser(jsonUser)
                                .enqueue(new retrofit2.Callback<UserAgorApp>() {
                                             @Override
                                             public void onResponse(Call<UserAgorApp> call, Response<UserAgorApp> response) {
                                                 Log.i("codi resposta", String.valueOf(response.code()));

                                                 prefs.deleteSession(); //Al fer logout, s'elimina la informació interna de l'app

                                                 Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Elimina totes less activities obertes
                                                 startActivity(i);

                                                 Toast.makeText(getApplicationContext(), "LOGOUT correcte, token refrescat", Toast.LENGTH_LONG).show();
                                             }

                                             @Override
                                             public void onFailure(Call<UserAgorApp> call, Throwable t) {
                                                 Toast.makeText(getApplicationContext(), "FAIL al logout", Toast.LENGTH_LONG).show();
                                                 new DialogServerKO(MainActivity.this).show();
                                             }
                                         });
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}
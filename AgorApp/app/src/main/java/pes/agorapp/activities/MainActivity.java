package pes.agorapp.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Chat;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.fragments.AnnouncementFragment;
import pes.agorapp.fragments.AnnouncementListFragment;
import pes.agorapp.fragments.ChatFragment;
import pes.agorapp.fragments.ChatListFragment;
import pes.agorapp.fragments.CouponListFragment;
import pes.agorapp.fragments.FormAnnouncementFragment;
import pes.agorapp.fragments.MapFragment;
import pes.agorapp.fragments.MarketplaceFragment;
import pes.agorapp.fragments.ProfileFragment;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;


public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AnnouncementFragment.OnFragmentInteractionListener,
        AnnouncementListFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        ChatListFragment.OnFragmentInteractionListener,
        MarketplaceFragment.OnFragmentInteractionListener,
        CouponListFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        checkPermissions();

        MapFragment mapFragment = new MapFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mapFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        ImageView logo = (ImageView) findViewById(R.id.logo_agorapp);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "logo", Toast.LENGTH_LONG).show();
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

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
            }
        }
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

        if (id == R.id.profile) {
            ProfileFragment profileFragment = new ProfileFragment();
            //CAL REFACTORING!!!
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.announcement_list) {
            AnnouncementListFragment listFragment = new AnnouncementListFragment();
            listFragment.setArguments(getIntent().getExtras());
            //CAL REFACTORING!!!
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, listFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.coupons_list) {
            CouponListFragment listFragment = new CouponListFragment();
            listFragment.setArguments(getIntent().getExtras());
            //CAL REFACTORING!!!
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, listFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_chat) {
            ChatListFragment chatListFragment = new ChatListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, chatListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
//            chatListFragment.setSettings();
        } else if (id == R.id.nav_manage) {
            //printProfile();
        } else if (id == R.id.nav_share) {
            //printProfile();
        } else if (id == R.id.nav_send) {
            //printProfile();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    @Override
    public void onAnnouncementSelected(Announcement announcement) {
        // Call the other fragment
        // Create fragment and give it an argument specifying the article it should show
        AnnouncementFragment newFragment = new AnnouncementFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment, "anuncement");
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        newFragment.setAnnouncement(announcement);
    }

    @Override
    public void createNewAnnouncement() {
        FormAnnouncementFragment formAnnouncementFragment = new FormAnnouncementFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, formAnnouncementFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCommentSelected(Comment comment) {
        Toast.makeText(getApplicationContext(), "comment", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNecessaryReload() {
        // Reload current fragment
        Fragment frg = null;
        frg = getSupportFragmentManager().findFragmentByTag("anuncement");
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    @Override
    public void onMarketplaceOpen() {
        // Call the other fragment
        // Create fragment and give it an argument specifying the article it should show
        MarketplaceFragment newFragment = new MarketplaceFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onCouponSelected(Coupon coupon) {
        Dialog dialogCoupon = new Dialog(this);
        dialogCoupon.setContentView(R.layout.show_coupon);
        dialogCoupon.show();

        TextView tvBotiga = (TextView) dialogCoupon.findViewById(R.id.coupon_text_establishment);
        TextView tvDiscount = (TextView) dialogCoupon.findViewById(R.id.coupon_text_discount);
        TextView tvPrice = (TextView) dialogCoupon.findViewById(R.id.coupon_text_price);

        tvBotiga.setText(coupon.getEstablishment());
        tvDiscount.setText(String.valueOf(coupon.getDiscount()) + "%");
        tvPrice.setText(String.valueOf(coupon.getPrice()) + " AgoraCoins");

        final PreferencesAgorApp prefs = new PreferencesAgorApp(this);
        final int coupon_id = coupon.getId();

        //Delete
        Button deleteButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDeleteCoupon();
            }
        });

        //Edit
        Button editButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogFormEdit = new Dialog(MainActivity.this);
                dialogFormEdit.setContentView(R.layout.form_publish_marketplace);
                dialogFormEdit.show();
                //Podem utilitzar el mateix formulari per crear que per editar el val
                Button confirmEditButton = (Button) dialogFormEdit.findViewById(R.id.btn_marketplace_publish);

                confirmEditButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //crida API edit coupon
                    }
                });
            }
        });

        //Buy
        Button buyButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //crida API buy coupon
                AgorAppApiManager
                        .getService()
                        .buyCoupon(Integer.valueOf(prefs.getId()), prefs.getActiveToken(), coupon_id)
                        .enqueue(new retrofit2.Callback<Coupon>() {
                            @Override
                            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                                //Coupon coupon_resp = response.body();
                            }

                            @Override
                            public void onFailure(Call<Coupon> call, Throwable t) {
                                System.out.println("Something went wrong!");
                                new DialogServerKO(MainActivity.this).show();
                            }
                        });
            }
        });

        if (!String.valueOf(coupon.getUser_id()).equals(prefs.getId())) {
            deleteButton.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
        }
    }

    private void popUpDeleteCoupon() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setTitle("Segur que vols esborrar aquest xec?");

        Drawable icon = getResources().getDrawable(android.R.drawable.ic_dialog_alert);
        icon.setTint(getColor(R.color.colorButtons));
        adb.setIcon(icon);

        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Oferta esborrada (manca crida API)", Toast.LENGTH_LONG).show();
            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        adb.show();
    }

    @Override
    public void onChatSelected(Chat chat) {
        // Call the other fragment
        // Create fragment and give it an argument specifying the article it should show
        ChatFragment newFragment = new ChatFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment, "chat");
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        newFragment.setChat(chat);
    }
}
package pes.agorapp.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.gson.JsonObject;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Chat;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.JSONObjects.Location;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.fragments.AnnouncementFragment;
import pes.agorapp.fragments.AnnouncementListFragment;
import pes.agorapp.fragments.ChatFragment;
import pes.agorapp.fragments.ChatListFragment;
import pes.agorapp.fragments.CouponListFragment;
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

    private PreferencesAgorApp prefs;
    private Location locAnn;
    private Dialog dialogForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        prefs = new PreferencesAgorApp(this);

        checkPermissions();

        MapFragment mapFragment = new MapFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, mapFragment);

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
        int id = item.getItemId();

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
        AnnouncementFragment newFragment = new AnnouncementFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment, "anuncement");
        transaction.addToBackStack(null);

        transaction.commit();

        newFragment.setAnnouncement(announcement);
    }

    @Override
    public void createNewAnnouncement() {

        dialogForm = new Dialog(this);
        dialogForm.setContentView(R.layout.form_announcement);
        dialogForm.show();

        final EditText etTitle = (EditText) dialogForm.findViewById(R.id.form_announcement_titleEdit);
        final EditText etDesc = (EditText) dialogForm.findViewById(R.id.form_announcement_descriptionEdit);
        final SeekBar sbReward = (SeekBar) dialogForm.findViewById(R.id.form_announcement_rewardEdit); //Limited value of seekbar to be max=1000
        final TextView rew = (TextView) dialogForm.findViewById(R.id.form_announcement_reward);

        Button btn_form_announcement_publish = (Button) dialogForm.findViewById(R.id.btn_form_announcement_publish);
        btn_form_announcement_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String desc = etDesc.getText().toString();
                String reward = String.valueOf(sbReward.getProgress());

                JsonObject jsonAnn = new JsonObject();
                jsonAnn.addProperty("title",title);
                jsonAnn.addProperty("description",desc);
                jsonAnn.addProperty("reward",reward);
                jsonAnn.addProperty("latitude",locAnn.getLatitude());
                jsonAnn.addProperty("longitude", locAnn.getLongitude());

                final JsonObject ann = new JsonObject();
                ann.add("anunci",jsonAnn);

                int user_id = Integer.valueOf(prefs.getId());
                String active_token = prefs.getActiveToken();

                AgorAppApiManager
                        .getService()
                        .createAnnouncement(user_id,active_token,ann)
                        .enqueue(new retrofit2.Callback<Announcement>() {
                            @Override
                            public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                                Announcement announcement = response.body();
                                //Toast.makeText(getActivity(), String.valueOf(locAnn.getLatitude()), Toast.LENGTH_LONG).show();
                                //Toast.makeText(getActivity(), announcement.getDescription(), Toast.LENGTH_LONG).show();
                                //Falta implementar el que ve a continuacio
                            }

                            @Override
                            public void onFailure(Call<Announcement> call, Throwable t) {
                                System.out.println("Unable to create the announcement!");
                                new DialogServerKO(MainActivity.this).show();
                            }
                        });
            }
        });

        sbReward.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                rew.setText("Recompensa oferta: " + String.valueOf(progress) + " AgoraPoints");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        PlaceAutocompleteFragment autocompFrag = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();
        autocompFrag.setFilter(typeFilter);

        autocompFrag.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName());//get place details here
                locAnn = new Location(place.getLatLng().latitude,place.getLatLng().longitude);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
            }
        });

        dialogForm.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                android.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.remove(getFragmentManager().findFragmentByTag("place_verify_form"));
                ft.commit();
            }
        });
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
    public void onCouponSelected(final Coupon coupon) {
        Dialog dialogCoupon = new Dialog(this);
        dialogCoupon.setContentView(R.layout.show_coupon);
        dialogCoupon.show();

        TextView tvBotiga = (TextView) dialogCoupon.findViewById(R.id.coupon_text_establishment);
        TextView tvDiscount = (TextView) dialogCoupon.findViewById(R.id.coupon_text_discount);
        TextView tvPrice = (TextView) dialogCoupon.findViewById(R.id.coupon_text_price);

        tvBotiga.setText(String.valueOf(coupon.getShopId()));

        String discount = String.valueOf(coupon.getDiscount()) + "%";
        tvDiscount.setText(discount);

        String price = String.valueOf(coupon.getPrice()) + " AgoraCoins";
        tvPrice.setText(price);

        //Delete
        Button deleteButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDeleteCoupon();
            }
        });

        //Customers
<<<<<<< HEAD
        Button customersButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_clients);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrar compradors
=======
        Button customersButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_customers);
        customersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popUpDeleteCoupon();
>>>>>>> 621727c0d3f25ce0dd3eb0957e5d82613ba66079
            }
        });

        //Buy
        Button buyButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_buy);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AgorAppApiManager
                        .getService()
                        .buyCoupon(Integer.valueOf(prefs.getId()), Integer.valueOf(prefs.getId()), prefs.getActiveToken(), coupon.getId())
                        .enqueue(new retrofit2.Callback<Coupon>() {
                            @Override
                            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                                //cupó comprat
                            }

                            @Override
                            public void onFailure(Call<Coupon> call, Throwable t) {
                                System.out.println("Something went wrong!");
                            }
                        });
            }
        });

        if (coupon.getShopId().equals(prefs.getShopId())) {
            System.out.println(coupon.getShopId());
            System.out.println(prefs.getShopId());
            deleteButton.setVisibility(View.GONE);
            customersButton.setVisibility(View.GONE);
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
        ChatFragment newFragment = new ChatFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment, "chat");
        transaction.addToBackStack(null);

        transaction.commit();
        newFragment.setChat(chat);
    }
}
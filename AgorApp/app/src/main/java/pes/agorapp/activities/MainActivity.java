package pes.agorapp.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import pes.agorapp.JSONObjects.Trophy;
import pes.agorapp.JSONObjects.UserAgorApp;
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
import pes.agorapp.fragments.SwapAnnouncementBid;
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
        CouponListFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener{

    private PreferencesAgorApp prefs;
    private Location locAnn;
    private Dialog dialogForm, dialogCoupon, dialogTrophy;

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
                Intent intent = getIntent();
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "logo", Toast.LENGTH_LONG).show();
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

        if (id == R.id.nav_home) {
            Intent intent = getIntent();
            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            ProfileFragment profileFragment = new ProfileFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_announcement_list) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            AnnouncementListFragment listFragment = new AnnouncementListFragment();
            listFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, listFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_coupons_list) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            CouponListFragment listFragment = new CouponListFragment();
            listFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, listFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_chat) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            ChatListFragment chatListFragment = new ChatListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, chatListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
//            chatListFragment.setSettings();
        } else if (id == R.id.nav_marketplace) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            MarketplaceFragment marketplaceFragment = new MarketplaceFragment();
            marketplaceFragment.setArguments(getIntent().getExtras());
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, marketplaceFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_settings) {
            //printProfile();
        } else if (id == R.id.nav_logout) {
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
        SwapAnnouncementBid swapAnnouncementBidFragment = new SwapAnnouncementBid();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, swapAnnouncementBidFragment, "swap");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        swapAnnouncementBidFragment.setAnnouncement(announcement);

        /*AnnouncementFragment newFragment = new AnnouncementFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, newFragment, "anuncement");
        transaction.addToBackStack(null);

        transaction.commit();

        newFragment.setAnnouncement(announcement);*/
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

                if (!validateForm(title, desc, reward, locAnn)) {
                    //dialogForm.dismiss();
                    final android.app.AlertDialog alertDialogValidate = new android.app.AlertDialog.Builder(MainActivity.this).create();
                    alertDialogValidate.setTitle(getString(R.string.errorAlert));
                    alertDialogValidate.setMessage(getString(R.string.allFieldsForm));
                    alertDialogValidate.setIcon(R.drawable.ic_warning_black_24dp);

                    alertDialogValidate.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialogValidate.dismiss();
                        }
                    });

                    alertDialogValidate.show();
                }
                else {
                    JsonObject jsonAnn = new JsonObject();
                    jsonAnn.addProperty("title", title);
                    jsonAnn.addProperty("description", desc);
                    jsonAnn.addProperty("reward", reward);
                    jsonAnn.addProperty("latitude", locAnn.getLatitude());
                    jsonAnn.addProperty("longitude", locAnn.getLongitude());

                    final JsonObject ann = new JsonObject();
                    ann.add("anunci", jsonAnn);

                    int user_id = Integer.valueOf(prefs.getId());
                    String active_token = prefs.getActiveToken();

                    AgorAppApiManager
                            .getService()
                            .createAnnouncement(user_id, active_token, ann)
                            .enqueue(new retrofit2.Callback<Announcement>() {
                                @Override
                                public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                                    Announcement announcement = response.body();
                                    dialogForm.dismiss();

                                    final android.app.AlertDialog alertDialogAnnouncementCreated = new android.app.AlertDialog.Builder(MainActivity.this).create();
                                    alertDialogAnnouncementCreated.setTitle(getString(R.string.announcementCreated));
                                    alertDialogAnnouncementCreated.setMessage(getString(R.string.announcementCreatedSubtitle));
                                    alertDialogAnnouncementCreated.setIcon(R.drawable.ic_info_black_24dp);

                                    alertDialogAnnouncementCreated.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialogAnnouncementCreated.dismiss();
                                        }
                                    });

                                    alertDialogAnnouncementCreated.show();
                                }

                                @Override
                                public void onFailure(Call<Announcement> call, Throwable t) {
                                    System.out.println("Unable to create the announcement!");
                                    new DialogServerKO(MainActivity.this).show();
                                }
                            });
                }
            }
        });

        sbReward.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                rew.setText(getString(R.string.rewardOffered) + ": " + String.valueOf(progress) + " AgoraCoins");
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

    private Boolean validateForm(String title, String desc, String reward, Location locAnn) {
        return !(title.equals("") || desc.equals("") || reward.equals("") || locAnn==null);
    }

    @Override
    public void onCommentSelected(Comment comment) {
        Toast.makeText(getApplicationContext(), "comment", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNecessaryReload(Announcement announcement) {
        // Reload current fragment
        Fragment frg = null;
        frg = getSupportFragmentManager().findFragmentByTag("swap");
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        //frg.setAnnouncement(announcement);
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
        dialogCoupon = new Dialog(this);
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
        final Button deleteButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDeleteCoupon(coupon.getId());
            }
        });

        //Customers
        Button customersButton = (Button) dialogCoupon.findViewById(R.id.btn_coupon_customers);
        customersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popUpDeleteCoupon();
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
                        .enqueue(new retrofit2.Callback<UserAgorApp>() {
                            @Override
                            public void onResponse(Call<UserAgorApp> call, Response<UserAgorApp> response) {
                                if (response.code() == 200) {
                                    Integer newCoins = response.body().getCoins();
                                    prefs.setCoins(newCoins);

                                    dialogCoupon.dismiss();

                                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
                                    alertDialog.setTitle(getString(R.string.couponBought));
                                    alertDialog.setMessage(getString(R.string.couponBoughtSubtitle));
                                    alertDialog.setIcon(R.drawable.ic_info_black_24dp);

                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                    alertDialog.show();

                                    updateCoinsAndRating();

                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                    MarketplaceFragment marketplaceFragment = new MarketplaceFragment();
                                    marketplaceFragment.setArguments(getIntent().getExtras());
                                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment_container, marketplaceFragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                } else if (response.code() == 400) {
                                    //not enough money dude
                                    dialogCoupon.dismiss();

                                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
                                    alertDialog.setTitle("Not enough money dude");
                                    alertDialog.setMessage("És trist demanar, però més trist és robar cupons virtuals d'una app falsa");
                                    alertDialog.setIcon(R.drawable.ic_info_black_24dp);

                                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            alertDialog.dismiss();
                                        }
                                    });

                                    alertDialog.show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UserAgorApp> call, Throwable t) {
                                System.out.println("Something went wrong!");
                            }
                        });
            }
        });

        if (!coupon.getShopId().equals(prefs.getShopId())) {
            //System.out.println(coupon.getShopId());
            //System.out.println(prefs.getShopId());
            deleteButton.setVisibility(View.GONE);
            customersButton.setVisibility(View.GONE);
        }
    }



    @Override
    public void onTrophySelected(final Trophy trophy) {
        dialogTrophy = new Dialog(this);
        dialogTrophy.setContentView(R.layout.show_trophy);
        dialogTrophy.show();

        TextView title = (TextView) dialogTrophy.findViewById(R.id.trophy_title);
        TextView description = (TextView) dialogTrophy.findViewById(R.id.trophy_description);

        title.setText(String.valueOf(trophy.getTitle()));

        String desc = String.valueOf(trophy.getDescription());
        description.setText(desc);

    }

    private void updateCoinsAndRating() {
        AgorAppApiManager
                .getService()
                .getUser(Integer.valueOf(prefs.getId()), Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<UserAgorApp>() {
                    @Override
                    public void onResponse(Call<UserAgorApp> call, Response<UserAgorApp> response) {
                        prefs.setCoins(response.body().getCoins());
                        prefs.setRating(response.body().getAverage_evaluation());
                    }

                    @Override
                    public void onFailure(Call<UserAgorApp> call, Throwable t) {
                        new DialogServerKO(MainActivity.this).show();
                    }
                });
    }

    private void popUpDeleteCoupon(final Integer couponId) {
        final android.app.AlertDialog aDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
        aDialog.setTitle(getString(R.string.couponDeletePopUp));

        Drawable icon = getResources().getDrawable(android.R.drawable.ic_dialog_alert);
        icon.setTint(getColor(R.color.secondaryColor));
        aDialog.setIcon(icon);

        aDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AgorAppApiManager
                        .getService()
                        .deleteCoupon(couponId, Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                        .enqueue(new retrofit2.Callback<Coupon>() {
                            @Override
                            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                                dialogCoupon.dismiss();

                                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setTitle(getString(R.string.couponDeleted));
                                alertDialog.setMessage(getString(R.string.couponDeletedSubtitle));
                                alertDialog.setIcon(R.drawable.ic_info_black_24dp);

                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                    }
                                });

                                alertDialog.show();
                            }

                            @Override
                            public void onFailure(Call<Coupon> call, Throwable t) {
                                System.out.println("Something went wrong!");
                            }
                        });
            }
        });

        aDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        aDialog.show();
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
package pes.agorapp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Bid;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.fragments.SwapAnnouncementBid;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jona on 12/12/17.
 */

public class BidsAdapter extends ArrayAdapter<Bid> {

    private Announcement announcement;
    private int idCreador;
    private boolean accepted;
    private PreferencesAgorApp prefs;
    private Context context;

    public BidsAdapter(Context context, List<Bid> bids, Announcement announcemt, int idCreador) {
        super(context, 0, bids);
        this.announcement = announcemt;
        this.idCreador = idCreador;
        accepted = false;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Bid bid = getItem(position);
        prefs = new PreferencesAgorApp(getContext());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bids, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.item_bid_title);
        TextView text = (TextView) convertView.findViewById(R.id.item_bid_coins);
        Button acceptBtn = (Button) convertView.findViewById(R.id.item_bid_button_accept);
        Button payBtn = (Button) convertView.findViewById(R.id.item_bid_button_pay);
        // Populate the data into the template view using the data object
        title.setText(bid.getUser().getName());
        text.setText(String.valueOf(bid.getReward()) + "c");
        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject json = new JsonObject();
                json.addProperty("bid_id", bid.getId());
                json.addProperty("user_id", Integer.valueOf(prefs.getId()));
                json.addProperty("active_token", prefs.getActiveToken());
                AgorAppApiManager
                        .getService()
                        .acceptBid(Integer.valueOf(bid.getUser().getId()), announcement.getId(), json)
                        .enqueue(new retrofit2.Callback<Bid>() {
                            @Override
                            public void onResponse(Call<Bid> call, Response<Bid> response) {
                                Fragment frg = ((FragmentActivity)context).getSupportFragmentManager().findFragmentByTag("swap");
                                final FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                ft.remove(frg).commit();
                                SwapAnnouncementBid swapAnnouncementBidFragment = new SwapAnnouncementBid();
                                FragmentTransaction fragmentTransaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, swapAnnouncementBidFragment, "swap");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                swapAnnouncementBidFragment.setAnnouncement(announcement);
                            }

                            @Override
                            public void onFailure(Call<Bid> call, Throwable t) {
                                System.out.println("Something went wrong!");
                                Activity activity = (Activity) context;
                                new DialogServerKO(activity).show();
                            }
                        });
            }
        });

        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject json = new JsonObject();
                json.addProperty("user_id", Integer.valueOf(prefs.getId()));
                json.addProperty("active_token", prefs.getActiveToken());
                AgorAppApiManager
                        .getService()
                        .payBid(Integer.valueOf(bid.getUser().getId()), announcement.getId(), json)
                        .enqueue(new retrofit2.Callback<Bid>() {
                            @Override
                            public void onResponse(Call<Bid> call, Response<Bid> response) {
                                final Dialog dialog = new Dialog(context);

                                dialog.setContentView(R.layout.form_valorate);
                                dialog.show();

                                final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.form_valorate_rating);
                                Button accept = (Button) dialog.findViewById(R.id.form_valorate_button);
                                accept.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        JsonObject json = new JsonObject();
                                        JsonObject evaluation = new JsonObject();
                                        int num = (int) ratingBar.getRating();
                                        evaluation.addProperty("score", num);
                                        evaluation.addProperty("anunci_id", announcement.getId());
                                        json.add("evaluation", evaluation);
                                        json.addProperty("user_id", Integer.valueOf(prefs.getId()));
                                        json.addProperty("active_token", prefs.getActiveToken());
                                        AgorAppApiManager
                                                .getService()
                                                .rate(Integer.valueOf(bid.getUser().getId()), json)
                                                .enqueue(new retrofit2.Callback<Object>() {
                                                    @Override
                                                    public void onResponse(Call<Object> call, Response<Object> response) {
                                                        Intent intent = ((Activity) context).getIntent();
                                                        ((Activity) context).finish();
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                        context.startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Object> call, Throwable t) {
                                                        System.out.println("Something went wrong!");
                                                        Activity activity = (Activity) context;
                                                        new DialogServerKO(activity).show();
                                                    }
                                                });
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<Bid> call, Throwable t) {
                                System.out.println("Something went wrong!");
                                Activity activity = (Activity) context;
                                new DialogServerKO(activity).show();
                            }
                        });
            }
        });
        if(accepted) {
            acceptBtn.setVisibility(View.INVISIBLE);
        } else {
            payBtn.setVisibility(View.INVISIBLE);
        }
        if (!String.valueOf(idCreador).equals(prefs.getId())) {
            acceptBtn.setVisibility(View.INVISIBLE);
            payBtn.setVisibility(View.INVISIBLE);
        }

        // Return the completed view to render on screen
        return convertView;
    }

    public void setAccepted() {
        accepted = true;
    }
}

package pes.agorapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Bid;
import pes.agorapp.R;
import pes.agorapp.adapters.BidsAdapter;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

import static pes.agorapp.helpers.ObjectsHelper.getFakeBids;


public class BidsFragment extends Fragment {

    private View mView;
    private PreferencesAgorApp prefs;
    private Integer idAnunci;
    List<Bid> bids = new ArrayList<>();
    private Announcement announcement;
    public BidsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new PreferencesAgorApp(getActivity());
        if (getArguments() != null) {
            idAnunci = getArguments().getInt("id");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bids, container, false);
        AgorAppApiManager
                .getService()
                .getAnnouncement(idAnunci, Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<Announcement>() {
                    @Override
                    public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                        announcement = response.body();
                        inflateBids();
                    }

                    @Override
                    public void onFailure(Call<Announcement> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });

        return mView;
    }

    private void inflateBids() {
        final BidsAdapter adapter = new BidsAdapter(getActivity(), bids, announcement, Integer.valueOf(announcement.getUser().getId()));
        final ListView listView = (ListView) mView.findViewById(R.id.listViewBids);
        listView.setAdapter(adapter);
        AgorAppApiManager
                .getService()
                .getBidsAnnouncement(idAnunci, Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Bid>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Bid>> call, Response<ArrayList<Bid>> response) {
                        bids = response.body();
                        for (Bid bid : bids) {
                            if (bid.isAccepted()) adapter.setAccepted();
                        }
                        adapter.clear();
                        adapter.addAll(bids);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Bid>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });

        ImageButton newBid = (ImageButton) mView.findViewById(R.id.bids_new_button);
        newBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infateNewBid();
            }
        });
        if(announcement.getStatus().equals("closed"))
            newBid.setVisibility(View.INVISIBLE);
    }

    private void infateNewBid() {
        final Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(R.layout.form_new_bid);
        dialog.show();


        final EditText price = (EditText) dialog.findViewById(R.id.form_new_bid_price);
        price.setText(Integer.toString(announcement.getReward()));

        Button accept = (Button) dialog.findViewById(R.id.form_new_bid_button_new_bid);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObject request = new JsonObject();
                //System.out.println("TOKEN: " + prefs.getActiveToken() + " IDUSER: " + prefs.getId() + " IDANUNCI: " +idAnunci);
                JsonObject bid = new JsonObject();
                bid.addProperty("amount", Integer.valueOf(price.getText().toString()));
                bid.addProperty("anunci_id", idAnunci);
                request.add("bid", bid);
                request.addProperty("user_id", Integer.valueOf(prefs.getId()));
                request.addProperty("active_token", prefs.getActiveToken());


                //System.out.println("LLAMADA " + request);

                AgorAppApiManager
                        .getService()
                        .newBid(Integer.valueOf(prefs.getId()), request)
                        .enqueue(new retrofit2.Callback<Bid>() {
                            @Override
                            public void onResponse(Call<Bid> call, Response<Bid> response) {
                                dialog.dismiss();
                                inflateBids();
                            }

                            @Override
                            public void onFailure(Call<Bid> call, Throwable throwable) {
                                System.out.println("Something went wrong!");
                                new DialogServerKO(getActivity()).show();
                            }
                        });
            }
        });

        Button cancel = (Button) dialog.findViewById(R.id.form_new_bid_button_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

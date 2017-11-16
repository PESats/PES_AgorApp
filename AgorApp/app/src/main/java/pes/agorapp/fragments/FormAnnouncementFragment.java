package pes.agorapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.gson.JsonObject;


import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Location;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by marc on 6/11/17.
 */

public class FormAnnouncementFragment extends Fragment implements View.OnClickListener {
    private EditText etTitle;
    private EditText etDesc;
    private SeekBar sbReward;
    private Location locAnn;
    private TextView rew;
    private PreferencesAgorApp prefs;

    public FormAnnouncementFragment() {}

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = new PreferencesAgorApp(getActivity());
        return inflater.inflate(R.layout.fragment_form_announcement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //botó publish
        view.findViewById(R.id.btn_form_announcement_publish).setOnClickListener(this);

        etTitle = (EditText) view.findViewById(R.id.form_announcement_titleEdit);
        etDesc = (EditText) view.findViewById(R.id.form_announcement_descriptionEdit);
        sbReward = (SeekBar) view.findViewById(R.id.form_announcement_rewardEdit); //Limited value of seekbar to be max=1000
        rew = (TextView) view.findViewById(R.id.form_announcement_reward);

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


        PlaceAutocompleteFragment autocompFrag = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        //We only want the addresses, so we declare a filter to make sure of it
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
                //Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.btn_form_announcement_publish:
                create_announcement(v);
                break;
        }
    }

    private void create_announcement(View view) {

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
        ann.addProperty("user_id",prefs.getId());
        ann.addProperty("active_token",prefs.getActiveToken());
        ann.add("anunci",jsonAnn);



        AgorAppApiManager.getService().createAnnouncement(ann).enqueue(new retrofit2.Callback<Announcement>(){
            @Override
            public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                Announcement announcement = response.body();
                Toast.makeText(getActivity(), String.valueOf(locAnn.getLatitude()), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), announcement.getDescription(), Toast.LENGTH_LONG).show();
                //Falta implementar el que ve a continuacio
            }

            @Override
            public void onFailure(Call<Announcement> call, Throwable t) {
                System.out.println("Unable to create the announcement!");
                new DialogServerKO(getActivity()).show();
            }
        });

        //TextView myAwesomeTextView = (TextView) view.findViewById(R.id.form_announcement_title);
        //myAwesomeTextView.setText("hola");
    }
}

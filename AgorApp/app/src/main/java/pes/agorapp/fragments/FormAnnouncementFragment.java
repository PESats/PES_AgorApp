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

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;

/**
 * Created by marc on 6/11/17.
 */

public class FormAnnouncementFragment extends Fragment implements View.OnClickListener {
    private EditText etTitle;
    private EditText etDesc;
    private SeekBar sbReward;

    public FormAnnouncementFragment() {}

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
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

        PlaceAutocompleteFragment autocompFrag = (PlaceAutocompleteFragment)getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        //We only want the addresses, so we declare a filter to make sure of it
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();
        autocompFrag.setFilter(typeFilter);

        autocompFrag.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName());//get place details here
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


        //TextView myAwesomeTextView = (TextView) view.findViewById(R.id.form_announcement_title);
        //myAwesomeTextView.setText("hola");
    }
}

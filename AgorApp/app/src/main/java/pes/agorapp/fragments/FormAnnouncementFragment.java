package pes.agorapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;

/**
 * Created by marc on 6/11/17.
 */

public class FormAnnouncementFragment extends Fragment {

    public FormAnnouncementFragment() {}

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form_announcement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

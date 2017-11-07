package pes.agorapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;

import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;

/**
 * Created by marc on 6/11/17.
 */

public class FormAnnouncementFragment extends Fragment implements View.OnClickListener {

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
        EditText et = (EditText) view.findViewById(R.id.form_announcement_descriptionEdit);
        String s = et.getText().toString();
        //TextView myAwesomeTextView = (TextView) view.findViewById(R.id.form_announcement_title);
        //myAwesomeTextView.setText("hola");
    }
}

package pes.agorapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Trophy;
import pes.agorapp.JSONObjects.UserAgorApp;
import pes.agorapp.R;
import pes.agorapp.activities.LoginActivity;
import pes.agorapp.activities.MainActivity;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.helpers.AnnouncementsAdapter;
import pes.agorapp.helpers.ObjectsHelper;
import pes.agorapp.helpers.TrophiesAdapter;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by marc on 31/10/17.
 */

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private AnnouncementListFragment.OnFragmentInteractionListener mListener;
    private PreferencesAgorApp prefs;

    public ProfileFragment() {}

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = new PreferencesAgorApp(getActivity());
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //botons
        view.findViewById(R.id.profile_btn_logout).setOnClickListener(this);
        view.findViewById(R.id.profile_btn_marketplace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMarketplaceOpen();
            }
        });
        //info d'usuari i imatge
        printProfile(view);

        List<Trophy> trophies = new ArrayList<>();
        TrophiesAdapter adapter = new TrophiesAdapter(getActivity(), trophies);
        final GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        trophies = ObjectsHelper.getFakeTrophies();
        adapter.addAll(trophies);
    }

    private void printProfile(View view) {
        printTextProfile(view);
        printImageProfile(view);
    }

    private void printImageProfile(View view) {
        /*imatge*/
        if (!prefs.getImageUrl().equals("www.imatgedummy.com") && !prefs.getImageUrl().equals("") && prefs.getImageUrl() != null) {
            Picasso.with(getActivity().getApplicationContext())
                    .load(prefs.getImageUrl())
                    .resize(250, 250)
                    .into((ImageView) view.findViewById(R.id.profile_user_img));
        } else {
            Picasso.with(getActivity().getApplicationContext())
                    .load(R.drawable.avatar_face_1_)
                    .resize(250, 250)
                    .into((ImageView) view.findViewById(R.id.profile_user_img));
        }
    }

    private void printTextProfile(View view) {
        /*info d'usuari*/
        String userName = prefs.getUserName();
        TextView myAwesomeTextView = (TextView) view.findViewById(R.id.profile_name);
        myAwesomeTextView.setText(userName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AnnouncementListFragment.OnFragmentInteractionListener) {
            mListener = (AnnouncementListFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.profile_btn_logout:
                close_session();
                break;
        }
    }

    private void close_session() {
        new AlertDialog.Builder(getActivity())
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

                                        Intent i = new Intent(getActivity(), LoginActivity.class);
                                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //Elimina totes less activities obertes
                                        startActivity(i);

                                        Toast.makeText(getActivity().getApplicationContext(), "LOGOUT correcte, token refrescat", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onFailure(Call<UserAgorApp> call, Throwable t) {
                                        Toast.makeText(getActivity().getApplicationContext(), "FAIL al logout", Toast.LENGTH_LONG).show();
                                        new DialogServerKO(getActivity()).show();
                                    }
                                });
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }
}

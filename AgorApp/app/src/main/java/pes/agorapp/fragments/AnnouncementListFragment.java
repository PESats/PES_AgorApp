package pes.agorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.helpers.AnnouncementsAdapter;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;


public class AnnouncementListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    List<Announcement> announcements = new ArrayList<>();
    private PreferencesAgorApp prefs;


    public AnnouncementListFragment() {
        // Required empty public constructor
    }

    public static AnnouncementListFragment newInstance(String param1, String param2) {
        AnnouncementListFragment fragment = new AnnouncementListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs = new PreferencesAgorApp(getActivity());
        return inflater.inflate(R.layout.fragment_announcement_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onAnnouncementSelected(Announcement announcement);
        void createNewAnnouncement();
        void onMarketplaceOpen();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.createNewAnnouncement();
            }
        });

        final AnnouncementsAdapter adapter = new AnnouncementsAdapter(getActivity(), announcements);

        final ListView listView = (ListView) view.findViewById(R.id.listViewAnnouncement);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Announcement announcement = (Announcement) listView.getItemAtPosition(position);
                mListener.onAnnouncementSelected(announcement);
            }
        });
        listView.setAdapter(adapter);
        AgorAppApiManager
                .getService()
                .getAnnouncements(Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Announcement>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Announcement>> call, Response<ArrayList<Announcement>> response) {

                        announcements = response.body();
                        adapter.addAll(announcements);
                        Log.d("this is my array", "arr: " + response.body().toString());

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Announcement>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }
}

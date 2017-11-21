package pes.agorapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.helpers.AnnouncementsAdapter;
import pes.agorapp.helpers.CommentsAdapter;
import pes.agorapp.helpers.ObjectsHelper;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnnouncementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnnouncementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnouncementFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Announcement announcement;
    PreferencesAgorApp prefs;
    List<Comment> comments = new ArrayList<>();


    public AnnouncementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AnnouncementFragment.
     */
    public static AnnouncementFragment newInstance() {
        AnnouncementFragment fragment = new AnnouncementFragment();
        Bundle args = new Bundle();
        //args.putInt("index", announcement.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = new PreferencesAgorApp(getActivity());
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcement, container, false);
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
        void onCommentSelected(Comment comment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView title = (TextView) view.findViewById(R.id.announcement_title);
        title.setText(this.announcement.getTitle());

        final TextView text = (TextView) view.findViewById(R.id.announcement_text);
        text.setText(this.announcement.getDescription());

        final TextView author = (TextView) view.findViewById(R.id.announcement_author);
        author.setText(String.valueOf(this.announcement.getUser().getId()));

        //buttons
        Button buttonDelete = (Button) view.findViewById(R.id.announcement_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AnnouncementsAdapter.super.getContext(), "Esborrar: " + announcement.getId(), Toast.LENGTH_LONG).show();

                JsonObject ann = new JsonObject();
                ann.addProperty("user_id",prefs.getId());
                ann.addProperty("active_token",prefs.getActiveToken());

                AgorAppApiManager.getService().deleteAnnouncement(announcement.getId(),Integer.valueOf(prefs.getId()), prefs.getActiveToken()).enqueue(new retrofit2.Callback<Announcement>() {
                    @Override
                    public void onResponse(Call<Announcement> call, Response<Announcement> response) {
                        Integer code = response.code();
                        //Falta implementar el que ve a continuacio
                    }

                    @Override
                    public void onFailure(Call<Announcement> call, Throwable t) {

                    }
                });

            }});
        if (!String.valueOf(announcement.getUser_id()).equals(prefs.getId())) {
            buttonDelete.setVisibility(View.INVISIBLE);
        }
        // Create the adapter to convert the array to views
        final CommentsAdapter adapter = new CommentsAdapter(getActivity(), comments);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) view.findViewById(R.id.comments_list);
        listView.setAdapter(adapter);
        //comments = announcement.getComments();
        AgorAppApiManager
                .getService()
                .getComments(announcement.getId(), Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                //.getAnnouncements(16, "aujEXUFZaWPotQhujtd9cMzL")
                .enqueue(new retrofit2.Callback<ArrayList<Comment>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {

                        //Log.i("response code", String.valueOf(response.code()));
                        //Log.d("this is my array", "arr: " + response.body().toString());
                        comments = response.body();
                        adapter.addAll(comments);
                        Log.d("this is my array", "arr: " + response.body().toString());

                    }

                    @Override
                    public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }

    public void setAnnouncement(Announcement anAnnouncement) {
        this.announcement = anAnnouncement;
    }
}

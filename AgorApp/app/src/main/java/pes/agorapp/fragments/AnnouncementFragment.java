package pes.agorapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;
import pes.agorapp.activities.MainActivity;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.adapters.CommentsAdapter;
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
    private Dialog comment_dialog;
    private EditText new_comment;
    private int idAnunci;
    private View mView;


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
        prefs = new PreferencesAgorApp(getActivity());
        if (getArguments() != null) {
            idAnunci = getArguments().getInt("id");
            inflateAnunci();
        }
    }

    private void inflateAnunci() {
        AgorAppApiManager
                .getService()
                .getAnnouncement(idAnunci, Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<Announcement>() {
                    @Override
                    public void onResponse(Call<Announcement> call, Response<Announcement> response) {

                        //Log.i("response code", String.valueOf(response.code()));
                        //Log.d("this is my arra3y", "arr: " + response.body().toString());
                        announcement = response.body();
                        inflateAnnouncement();
                    }

                    @Override
                    public void onFailure(Call<Announcement> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        void onNecessaryReload(Announcement announcement);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;

        prefs = new PreferencesAgorApp(getActivity());




    }

    void inflateDialog() {
        comment_dialog = new Dialog(getActivity());

        comment_dialog.setContentView(R.layout.form_new_comment);
        comment_dialog.show();

        final EditText commentText = (EditText) comment_dialog.findViewById((R.id.form_new_comment_comment));

        Button publishButton = (Button) comment_dialog.findViewById(R.id.form_new_comment_button_new_comment);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JsonObject jsonUser = new JsonObject();
                jsonUser.addProperty("text", commentText.getText().toString());

                JsonObject json = new JsonObject();
                json.add("comentari", jsonUser);
                AgorAppApiManager
                        .getService()
                        .createAnnouncementComment(announcement.getId(), prefs.getId(), prefs.getActiveToken(), json)
                        .enqueue(new retrofit2.Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                //mListener.onNecessaryReload(announcement);
                                comment_dialog.dismiss();
                                inflateAnnouncement();

                                //comment_dialog.d
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                System.out.println("Something went wrong!");
                                new DialogServerKO(getActivity()).show();
                                //mListener.onNecessaryReload(announcement);
                            }
                        });


            }
        });

        Button cancelButton = (Button) comment_dialog.findViewById(R.id.form_new_comment_button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment_dialog.dismiss();
            }
        });
    }

    void inflateAnnouncement() {

        comment_dialog = new Dialog(getActivity());
        comment_dialog.setTitle("Nuevo comentario");
        new_comment = new EditText(getContext());

        final Button button = (Button) mView.findViewById(R.id.new_comment_announcement);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                inflateDialog();
            }
        });

        final Button buttonShareTwitter = (Button) view.findViewById(R.id.btn_share_twitter);
        buttonShareTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetComposer.Builder builder = new TweetComposer.Builder(getActivity())
                        .text(getString(R.string.twText1) + ": "
                                + announcement.getTitle()
                                + " " + getString(R.string.twText2) + " "
                                + announcement.getReward()
                                + " AgoraCoins!");
                builder.show();
            }
        });

    }

    void inflateAnnouncement() {
        final TextView title = (TextView) mView.findViewById(R.id.announcement_title);
        title.setText(this.announcement.getTitle());

        final TextView text = (TextView) mView.findViewById(R.id.announcement_text);
        text.setText(this.announcement.getDescription());

        final TextView author = (TextView) mView.findViewById(R.id.announcement_author);
        author.setText(String.valueOf(this.announcement.getUser().getId()));

        //buttons
        Button buttonDelete = (Button) mView.findViewById(R.id.announcement_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AnnouncementsAdapter.super.getContext(), "Esborrar: " + announcement.getId(), Toast.LENGTH_LONG).show();

                popUpDeleteAnnouncement(announcement.getId());

            }});
        if (!String.valueOf(announcement.getUser().getId()).equals(prefs.getId())) {
            buttonDelete.setVisibility(View.INVISIBLE);
        }
        // Create the adapter to convert the array to views
        final CommentsAdapter adapter = new CommentsAdapter(getActivity(), comments);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) mView.findViewById(R.id.comments_list);
        listView.setAdapter(adapter);
        //comments = announcement.getComments();
        AgorAppApiManager
                .getService()
                .getComments(announcement.getId(), Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Comment>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {

                        //Log.i("response code", String.valueOf(response.code()));
                        //Log.d("this is my array", "arr: " + response.body().toString());
                        comments = response.body();
                        adapter.clear();
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

    private void popUpDeleteAnnouncement(final int announcementId) {
        final android.app.AlertDialog aDialog = new android.app.AlertDialog.Builder(getActivity()).create();
        aDialog.setTitle(getString(R.string.announcementDeletePopUp));

        Drawable icon = getResources().getDrawable(android.R.drawable.ic_dialog_alert);
        icon.setTint(getResources().getColor(R.color.secondaryColor));
        aDialog.setIcon(icon);

        aDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AgorAppApiManager
                        .getService()
                        .deleteCoupon(announcementId, Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                        .enqueue(new retrofit2.Callback<Coupon>() {
                            @Override
                            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                                //dialogCoupon.dismiss();

                                final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle(getString(R.string.announcementDeleted));
                                alertDialog.setMessage(getString(R.string.announcementDeletedSubtitle));
                                alertDialog.setIcon(R.drawable.ic_info_black_24dp);

                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                    }
                                });

                                alertDialog.show();

                                getActivity().getSupportFragmentManager().popBackStack();
                                //getActivity().onBackPressed();
                            }

                            @Override
                            public void onFailure(Call<Coupon> call, Throwable t) {
                                System.out.println("Something went wrong!");
                            }
                        });
            }
        });

        aDialog.show();
    }
}

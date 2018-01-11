package pes.agorapp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.fragments.SwapAnnouncementBid;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Alex on 01-Nov-17.
 */

public class CommentsAdapter extends ArrayAdapter<Comment> {

    public CommentsAdapter(Context context, List<Comment> comments, Announcement announcement) {
        super(context, 0, comments);
        mContext = context;
        this.announcement = announcement;
    }
    private PreferencesAgorApp prefs;
    private Context mContext;
    Dialog comment_dialog;
    Announcement announcement;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Comment comment = getItem(position);
        prefs = new PreferencesAgorApp(getContext());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.comment_title);
        TextView text = (TextView) convertView.findViewById(R.id.comment_text);
        TextView date = (TextView) convertView.findViewById(R.id.comment_date);
        //ImageView imgView = (ImageView) convertView.findViewById(R.id.icon_comment);
        // Populate the data into the template view using the data object
        title.setText(comment.getUser().getName());
        text.setText(comment.getText());
        date.setText(comment.getDateString());
        //buttons
        Button buttonEdit = (Button) convertView.findViewById(R.id.comment_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment_dialog = new Dialog(mContext);

                comment_dialog.setContentView(R.layout.form_new_comment);
                comment_dialog.show();

                final EditText commentText = (EditText) comment_dialog.findViewById((R.id.form_new_comment_comment));
                commentText.setText(comment.getText());

                Button publishButton = (Button) comment_dialog.findViewById(R.id.form_new_comment_button_new_comment);

                publishButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        JsonObject jsonUser = new JsonObject();
                        jsonUser.addProperty("text", commentText.getText().toString());

                        JsonObject json = new JsonObject();
                        json.add("comentari", jsonUser);
                        json.addProperty("user_id", prefs.getId());
                        json.addProperty("active_token", prefs.getActiveToken());
                        AgorAppApiManager
                                .getService()
                                .editComment(comment.getAnunci_id(), comment.getId(), json)
                                .enqueue(new retrofit2.Callback<Comment>() {
                                    @Override
                                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                                        comment_dialog.dismiss();

                                        Fragment frg = ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentByTag("swap");
                                        final FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                                        ft.remove(frg).commit();
                                        SwapAnnouncementBid swapAnnouncementBidFragment = new SwapAnnouncementBid();
                                        FragmentTransaction fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment_container, swapAnnouncementBidFragment, "swap");
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();
                                        swapAnnouncementBidFragment.setAnnouncement(announcement);
                                    }

                                    @Override
                                    public void onFailure(Call<Comment> call, Throwable t) {
                                        System.out.println("Something went wrong!");
                                        new DialogServerKO((Activity) mContext).show();
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
        });
        Button buttonDelete = (Button) convertView.findViewById(R.id.comment_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*
                @Path("idA") int idA,
            @Path("idC") int idC,
            @Query("user_id") int user_id,
            @Query("active_token") String active_token
                 */
                AgorAppApiManager
                        .getService()
                        .deleteComment(comment.getAnunci_id(),  comment.getId(), Integer.valueOf(prefs.getId()), prefs.getActiveToken() )
                        .enqueue(new retrofit2.Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                Fragment frg = ((FragmentActivity)mContext).getSupportFragmentManager().findFragmentByTag("swap");
                                final FragmentTransaction ft = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                                ft.remove(frg).commit();
                                SwapAnnouncementBid swapAnnouncementBidFragment = new SwapAnnouncementBid();
                                FragmentTransaction fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragment_container, swapAnnouncementBidFragment, "swap");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                swapAnnouncementBidFragment.setAnnouncement(announcement);
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                //
                            }
                        });

            }
        });
        if (!comment.getUser().getId().equals(prefs.getId())) {
            buttonEdit.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);
        }
        // Return the completed view to render on screen
        return convertView;
    }

}

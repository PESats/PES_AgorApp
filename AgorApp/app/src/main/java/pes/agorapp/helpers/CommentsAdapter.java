package pes.agorapp.helpers;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import okhttp3.Request;
import pes.agorapp.JSONObjects.UserAgorApp;
import pes.agorapp.activities.LoginActivity;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import pes.agorapp.JSONObjects.Comment;
import pes.agorapp.R;
import pes.agorapp.network.AgorAppApiManager;

/**
 * Created by Alex on 01-Nov-17.
 */

public class CommentsAdapter extends ArrayAdapter<Comment> {

    public CommentsAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Comment comment = getItem(position);
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
        title.setText(comment.getAuthor());
        text.setText(comment.getContent());
        date.setText(comment.getDateString());
        //buttons
        Button buttonEdit = (Button) convertView.findViewById(R.id.comment_edit);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentsAdapter.super.getContext(), "Editar: " + comment.getId(), Toast.LENGTH_LONG).show();
                /*
                int commentId = comment.getId();

                PreferencesAgorApp prefs = new PreferencesAgorApp(CommentsAdapter.super.getContext());

                String userId = prefs.getId();
                String token = prefs.getActiveToken();

                JsonObject jsonUser = new JsonObject();
                jsonUser.addProperty("id", userId);
                jsonUser.addProperty("active_token", token);

                JsonObject json = new JsonObject();
                json.add("user", jsonUser);

                AgorAppApiManager
                        .getService()
                        .editComment(commentId, json)
                        .enqueue(new retrofit2.Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                //
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                //
                            }
                        });
                */
            }
        });
        Button buttonDelete = (Button) convertView.findViewById(R.id.comment_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommentsAdapter.super.getContext(), "Esborrar: " + comment.getId(), Toast.LENGTH_LONG).show();
                /*
                int commentId = comment.getId();

                PreferencesAgorApp prefs = new PreferencesAgorApp(CommentsAdapter.super.getContext());

                String userId = prefs.getId();
                String token = prefs.getActiveToken();

                JsonObject jsonUser = new JsonObject();
                jsonUser.addProperty("id", userId);
                jsonUser.addProperty("active_token", token);

                JsonObject json = new JsonObject();
                json.add("user", jsonUser);

                AgorAppApiManager
                        .getService()
                        .deleteComment(commentId, json)
                        .enqueue(new retrofit2.Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                //
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                //
                            }
                        });
                */
            }
        });
        if (comment.getId() != 1) {
            buttonEdit.setVisibility(View.INVISIBLE);
            buttonDelete.setVisibility(View.INVISIBLE);
        }
        // Return the completed view to render on screen
        return convertView;
    }
}

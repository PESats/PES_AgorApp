package pes.agorapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Alex on 01-Nov-17.
 */

public class AnnouncementsAdapter extends ArrayAdapter<Announcement> {

    public AnnouncementsAdapter(Context context, List<Announcement> announcements) {
        super(context, 0, announcements);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Announcement announcement = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_announcement, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.item_title);
        TextView text = (TextView) convertView.findViewById(R.id.item_text);
        TextView date = (TextView) convertView.findViewById(R.id.item_date);
        // Populate the data into the template view using the data object
        title.setText(announcement.getTitle());
        text.setText(announcement.getDescription());
        date.setText(announcement.getCreated_at().toString());

        //buttons
        Button buttonDelete = (Button) convertView.findViewById(R.id.announcement_delete);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AnnouncementsAdapter.super.getContext(), "Esborrar: " + announcement.getId(), Toast.LENGTH_LONG).show();
                PreferencesAgorApp prefs = new PreferencesAgorApp(AnnouncementsAdapter.super.getContext());

                JsonObject jsonUser = new JsonObject();
                jsonUser.addProperty("id",prefs.getId());
                jsonUser.addProperty("active_token",prefs.getActiveToken());

                JsonObject user = new JsonObject();
                user.add("user",jsonUser);

                AgorAppApiManager.getService().deleteAnnouncement(announcement.getId(), user).enqueue(new retrofit2.Callback<Announcement>() {
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


        // Return the completed view to render on screen
        return convertView;
    }
}

package pes.agorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pes.agorapp.JSONObjects.Announcement;
import pes.agorapp.R;

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
        //TextView date = (TextView) convertView.findViewById(R.id.item_date);
        // Populate the data into the template view using the data object

        String day = String.valueOf(announcement.getCreated_at().getDay());
        String month = String.valueOf(announcement.getCreated_at().getMonth());
        String hours = String.valueOf(announcement.getCreated_at().getHours());
        String minutes = String.valueOf(announcement.getCreated_at().getMinutes());
        if (lessThanTen(minutes)) minutes = "0" + minutes;
        String dateCreated = day + "/" + month + " " + hours + ":" + minutes;

        title.setText(announcement.getTitle());
        text.setText(String.valueOf(announcement.getReward()) + " AgoraCoins");
        //date.setText(announcement.getCreated_at().toString());

        // Return the completed view to render on screen
        return convertView;
    }

    private boolean lessThanTen(String minutes) {
        return minutes.equals("0")
                || minutes.equals("1")
                || minutes.equals("2")
                || minutes.equals("3")
                || minutes.equals("5")
                || minutes.equals("6")
                || minutes.equals("7")
                || minutes.equals("8")
                || minutes.equals("9");
    }
}

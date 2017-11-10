package pes.agorapp.helpers;

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

    public AnnouncementsAdapter(Context context, List<Announcement> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Announcement announcement = getItem(position);
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
        // Return the completed view to render on screen
        return convertView;
    }
}

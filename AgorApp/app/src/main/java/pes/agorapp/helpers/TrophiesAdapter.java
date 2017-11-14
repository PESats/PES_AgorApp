package pes.agorapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import pes.agorapp.JSONObjects.Trophy;
import pes.agorapp.R;

/**
 * Created by marc on 14/11/17.
 */

public class TrophiesAdapter extends ArrayAdapter<Trophy> {

    public TrophiesAdapter(Context context, List<Trophy> trophies) {
        super(context, 0, trophies);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Trophy trophy = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trophy, parent, false);
        }
        // Lookup view for data population

        // Populate the data into the template view using the data object

        return convertView;
    }
}

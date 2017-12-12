package pes.agorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pes.agorapp.JSONObjects.Bid;
import pes.agorapp.R;

/**
 * Created by jona on 12/12/17.
 */

public class BidsAdapter extends ArrayAdapter<Bid> {

    public BidsAdapter(Context context, List<Bid> bids) {
        super(context, 0, bids);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Bid bid = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bids, parent, false);
        }
        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.item_bid_title);
        TextView text = (TextView) convertView.findViewById(R.id.item_bid_coins);
        // Populate the data into the template view using the data object
        title.setText(bid.getUser().getName());
        text.setText(String.valueOf(bid.getReward()) + "c");

        // Return the completed view to render on screen
        return convertView;
    }
}

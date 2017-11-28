package pes.agorapp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;

/**
 * Created by Nil on 25/11/2017.
 */

public class CouponAdapter extends ArrayAdapter<Coupon> {
    private PreferencesAgorApp prefs;

    public CouponAdapter(Context context, List<Coupon> coupons) {
        super(context, 0, coupons);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Coupon coupon = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_coupon, parent, false);
        }

        // Lookup view for data population
        TextView title = (TextView) convertView.findViewById(R.id.item_title);
        // Populate the data into the template view using the data object
        title.setText(coupon.getTitle());

        // Return the completed view to render on screen
        return convertView;
    }


}

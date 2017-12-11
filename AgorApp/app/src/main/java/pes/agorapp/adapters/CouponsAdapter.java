package pes.agorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;

/**
 * Created by Nil on 05-Dec-17.
 */

public class CouponsAdapter extends ArrayAdapter<Coupon> {

    public CouponsAdapter(Context context, List<Coupon> coupons) {
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
        TextView estab = (TextView) convertView.findViewById(R.id.item_coupon_establishment);
        TextView disc = (TextView) convertView.findViewById(R.id.item_coupon_discount);
        TextView price = (TextView) convertView.findViewById(R.id.item_coupon_price);
        // Populate the data into the template view using the data object
        estab.setText(coupon.getShopId());
        disc.setText(coupon.getDiscount());
        price.setText(coupon.getPrice());

        // Return the completed view to render on screen
        return convertView;
    }
}

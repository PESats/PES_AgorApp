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
        final Coupon coupon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_coupon, parent, false);
        }

        TextView estab = (TextView) convertView.findViewById(R.id.item_coupon_establishment);
        estab.setText("shopName not found");

        TextView disc = (TextView) convertView.findViewById(R.id.item_coupon_discount);
        disc.setText("discount not found al json");

        TextView price = (TextView) convertView.findViewById(R.id.item_coupon_price);
        String priceValue = String.valueOf(coupon.getPrice())+" AgoraCoins";
        price.setText("no hi ha price al json");

        return convertView;
    }
}

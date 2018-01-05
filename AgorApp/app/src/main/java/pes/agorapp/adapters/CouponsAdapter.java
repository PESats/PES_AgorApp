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
 * Created by Nil on 25/11/2017.
 */

public class CouponsAdapter extends ArrayAdapter<Coupon> {

    public CouponsAdapter(Context context, int resource, List<Coupon> coupons) {
        super(context, resource, coupons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Coupon coupon = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_coupon, parent, false);
        }

        TextView establishment = (TextView) convertView.findViewById(R.id.item_coupon_establishment);
        establishment.setText(coupon.getBotiga().getName());

        TextView textPrice = (TextView) convertView.findViewById(R.id.item_coupon_text);
        textPrice.setText("Preu: ");

        TextView price = (TextView) convertView.findViewById(R.id.item_coupon_price);
        String priceValue = String.valueOf(coupon.getPrice())+" AgoraCoins";
        price.setText(priceValue);

        TextView discount = (TextView) convertView.findViewById(R.id.item_coupon_discount);
        String discountValue = String.valueOf(coupon.getDiscount())+"%";
        discount.setText(discountValue);


        if (coupon.getPrice() == 0) {
            textPrice.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
        }

        return convertView;
    }


}
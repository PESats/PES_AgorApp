package pes.agorapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.JSONObjects.UserAgorApp;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.adapters.CouponsAdapter;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by marc on 6/11/17.
 */
public class MarketplaceFragment extends Fragment implements View.OnClickListener {

    private MarketplaceFragment.OnFragmentInteractionListener mListener;
    List<Coupon> coupons = new ArrayList<>();
    private PreferencesAgorApp prefs;
    private Dialog dialogForm;
    private Button marketplace_publish;
    private SeekBar sbDiscount, sbPrice;
    private TextView disc, pri;
    private Integer discount, price;
    private CouponsAdapter adapter;
    private ListView listView;

    public MarketplaceFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        prefs = new PreferencesAgorApp(getActivity());

        return inflater.inflate(R.layout.fragment_marketplace, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onCouponSelected(Coupon coupon);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CouponsAdapter(getActivity(), R.id.listCoupons, coupons);

        listView = (ListView) view.findViewById(R.id.listCoupons);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coupon coupon = (Coupon) listView.getItemAtPosition(position);
                mListener.onCouponSelected(coupon);
            }
        });
        listView.setAdapter(adapter);

        AgorAppApiManager
                .getService()
                .getCoupons(Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Coupon>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Coupon>> call, Response<ArrayList<Coupon>> response) {
                        coupons = response.body();
                        adapter.addAll(coupons);
                        Log.d("this is my array", "arr: " + response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Coupon>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });

        view.findViewById(R.id.btn_marketplace_publish).setOnClickListener(this);

        if (!prefs.hasShop()) {
            view.findViewById(R.id.btn_marketplace_publish).setVisibility(View.GONE);
            view.findViewById(R.id.text_marketplace_publish).setVisibility(View.GONE);
            view.findViewById(R.id.merchantHeader).setVisibility(View.GONE);
        } else {
            TextView shop = (TextView) view.findViewById(R.id.text_marketplace_publish);
            shop.setText("Com a comerciant verificat, pots publicar cupons per a la teva botiga " + prefs.getShopName());
        }

        updateCoinsAndRating();
        printCoins(view);
    }

    private void printCoins(View view) {
        String walletText = prefs.getCoins() + " AgoraCoins";
        TextView wallet = (TextView) view.findViewById(R.id.marketplace_wallet_coins);
        wallet.setText(walletText);
    }

    private void updateCoinsAndRating() {
        AgorAppApiManager
                .getService()
                .getUser(Integer.valueOf(prefs.getId()), Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<UserAgorApp>() {
                    @Override
                    public void onResponse(Call<UserAgorApp> call, Response<UserAgorApp> response) {
                        prefs.setCoins(response.body().getCoins());
                        prefs.setRating(response.body().getAverage_evaluation());
                    }

                    @Override
                    public void onFailure(Call<UserAgorApp> call, Throwable t) {
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }

    @Override
    public void onClick (View v){
        switch (v.getId()) {
            case R.id.btn_marketplace_publish:
                marketplace_publish();
                break;
        }
    }

    private void marketplace_publish() {
        dialogForm = new Dialog(getActivity());
        dialogForm.setContentView(R.layout.form_publish_marketplace);
        dialogForm.show();

        TextView tvShopName = (TextView) dialogForm.findViewById((R.id.form_marketplace_title));
        tvShopName.setText(prefs.getShopName());

        sbDiscount = (SeekBar) dialogForm.findViewById(R.id.seekBar_discount);
        disc = (TextView) dialogForm.findViewById(R.id.discount_percentage);

        sbDiscount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                disc.setText(String.valueOf(progress) + "%");
                discount = progress;
                System.out.println(discount);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbPrice = (SeekBar) dialogForm.findViewById(R.id.seekBar_price);
        pri = (TextView) dialogForm.findViewById(R.id.price_absolute);

        sbPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                pri.setText(String.valueOf(progress) + " AgoraCoins");
                price = progress;
                System.out.println(price);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button publishButton = (Button) dialogForm.findViewById(R.id.btn_marketplace_publish);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("discount: " + discount);
                System.out.println("price: " + price);

                JsonObject json = new JsonObject();
                json.addProperty("title", prefs.getShopName());
                //json.addProperty("description", "no obligatori");
                json.addProperty("price", price);
                json.addProperty("discount", discount);
                json.addProperty("shop_id", prefs.getShopId());

                JsonObject jsonCoupon = new JsonObject();
                jsonCoupon.add("coupon", json);

                AgorAppApiManager
                        .getService()
                        .createCoupon(Integer.valueOf(prefs.getId()), prefs.getActiveToken(), jsonCoupon)
                        .enqueue(new retrofit2.Callback<Coupon>() {
                            @Override
                            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                                //System.out.println(response.code());
                                //System.out.println(response.body().getPrice());
                                dialogForm.dismiss();

                                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                                alertDialog.setTitle(getString(R.string.couponCreated));
                                alertDialog.setMessage(getString(R.string.couponCreatedSubtitle) + " " + prefs.getShopName());
                                alertDialog.setIcon(R.drawable.ic_info_black_24dp);

                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        alertDialog.dismiss();
                                    }
                                });

                                alertDialog.show();
                                /*
                                listView.setAdapter(null);
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.detach(MarketplaceFragment.this).attach(MarketplaceFragment.this).commit();
                                */
                                listView.invalidate();
                                //coupons.add(response.body());
                                //adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<Coupon> call, Throwable t) {
                                new DialogServerKO(getActivity()).show();
                            }
                        });
            }
        });
    }
}

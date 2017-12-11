package pes.agorapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.adapters.CouponAdapter;
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

        final CouponAdapter adapter = new CouponAdapter(getActivity(), coupons);

        final ListView listView = (ListView) view.findViewById(R.id.listCoupons);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coupon coupon = (Coupon) listView.getItemAtPosition(position);
                mListener.onCouponSelected(coupon);
            }
        });
        listView.setAdapter(adapter);

        //CRIDA A LA API PER A MOSTRAR TOTS ELS VALS
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

        sbDiscount = (SeekBar) dialogForm.findViewById(R.id.seekBar_discount);
        disc = (TextView) dialogForm.findViewById(R.id.discount_percentage);

        sbDiscount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                disc.setText(String.valueOf(progress) + "%");
                discount = progress;
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
                                System.out.println(response.code());
                                System.out.println(response.body().getPrice());
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

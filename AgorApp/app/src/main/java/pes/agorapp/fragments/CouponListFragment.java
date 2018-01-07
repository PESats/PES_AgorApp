package pes.agorapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;
import pes.agorapp.customComponents.DialogServerKO;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.adapters.CouponsAdapter;
import pes.agorapp.network.AgorAppApiManager;
import retrofit2.Call;
import retrofit2.Response;


public class CouponListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Coupon> coupons = new ArrayList<>();
    private PreferencesAgorApp prefs;
    private AlertDialog dialog;
    private Coupon selectedCoupon;
    private CouponsAdapter adapter;

    public CouponListFragment() {
        // Required empty public constructor
    }

    public static CouponListFragment newInstance(String param1, String param2) {
        CouponListFragment fragment = new CouponListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs = new PreferencesAgorApp(getActivity());
        return inflater.inflate(R.layout.fragment_coupon_list, container, false);
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

        void onMarketplaceOpen();

        void onCouponSelected(Coupon coupon);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.couponBuyDialog)
                .setTitle(R.string.couponBuyDialogTitle);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                AgorAppApiManager.getService()
                        .spendCoupon(Integer.valueOf(prefs.getId()), selectedCoupon.getId(), prefs.getActiveToken())
                        .enqueue(new retrofit2.Callback<Coupon>() {
                            @Override
                            public void onResponse(Call<Coupon> call, Response<Coupon> response) {
                                getCoupons(adapter);
                            }

                            @Override
                            public void onFailure(Call<Coupon> call, Throwable t) {
                                System.out.println("Something went wrong!");
                                new DialogServerKO(getActivity()).show();
                            }
                        });
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        // 3. Get the AlertDialog from create()
        dialog = builder.create();

        // Construct the data source
        // Create the adapter to convert the array to views
        adapter = new CouponsAdapter(getActivity(), R.id.listViewCoupon, coupons);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) view.findViewById(R.id.listViewCoupon);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coupon coupon = (Coupon) listView.getItemAtPosition(position);
                selectedCoupon = coupon;
                dialog.show();
            }
        });
        /*For debugging/postman purposes
        String idd = prefs.getId();
        String tokk = prefs.getActiveToken();
        */
        listView.setAdapter(adapter);
        getCoupons(adapter);
    }

    private void getCoupons(final CouponsAdapter adapter) {
        AgorAppApiManager
                .getService()
                .getBoughtCoupons(Integer.valueOf(prefs.getId()), prefs.getActiveToken())
                .enqueue(new retrofit2.Callback<ArrayList<Coupon>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Coupon>> call, Response<ArrayList<Coupon>> response) {
                        coupons = response.body();
                        adapter.clear();
                        adapter.addAll(coupons);
                        //adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Coupon>> call, Throwable t) {
                        System.out.println("Something went wrong!");
                        new DialogServerKO(getActivity()).show();
                    }
                });
    }
}

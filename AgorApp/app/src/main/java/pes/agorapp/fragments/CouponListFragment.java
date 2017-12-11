package pes.agorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.adapters.CouponsAdapter;


public class CouponListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    List<Coupon> coupons = new ArrayList<>();
    private PreferencesAgorApp prefs;


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

        // Construct the data source
        // Create the adapter to convert the array to views
        final CouponsAdapter adapter = new CouponsAdapter(getActivity(), coupons);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) view.findViewById(R.id.listViewCoupon);
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
                .getUserCoupons(Integer.valueOf(prefs.getId()), prefs.getActiveToken())
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
    }
}

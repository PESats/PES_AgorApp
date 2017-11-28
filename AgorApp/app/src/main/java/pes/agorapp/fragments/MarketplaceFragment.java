package pes.agorapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.helpers.CouponAdapter;

/**
 * Created by marc on 6/11/17.
 */

public class MarketplaceFragment extends Fragment {
    private MarketplaceFragment.OnFragmentInteractionListener mListener;
    List<Coupon> announcements = new ArrayList<>();
    private PreferencesAgorApp prefs;

    public MarketplaceFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
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

        // Construct the data source
        // Create the adapter to convert the array to views
        final CouponAdapter adapter = new CouponAdapter(getActivity(), announcements);
    // Attach the adapter to a ListView
        final ListView listView = (ListView) view.findViewById(R.id.listViewAnnouncement);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coupon coupon = (Coupon) listView.getItemAtPosition(position);
                mListener.onCouponSelected(coupon);
            }
        });
        listView.setAdapter(adapter);

        //CRIDA A LA API PER A MOSTRAR TOTS ELS VALS

    }

}

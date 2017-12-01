package pes.agorapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Coupon;
import pes.agorapp.R;
import pes.agorapp.globals.PreferencesAgorApp;
import pes.agorapp.helpers.CouponAdapter;
import pes.agorapp.helpers.ObjectsHelper;

/**
 * Created by marc on 6/11/17.
 */
public class MarketplaceFragment extends Fragment implements View.OnClickListener {

    private MarketplaceFragment.OnFragmentInteractionListener mListener;
    List<Coupon> announcements = new ArrayList<>();
    private PreferencesAgorApp prefs;
    private Dialog dialogForm;
    private Button marketplace_publish;
    private SeekBar sbDiscount;
    private TextView disc;

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
        List<Coupon> coupons = new ArrayList<>();
        final CouponAdapter adapter = new CouponAdapter(getActivity(), announcements);
        // Attach the adapter to a ListView
        final ListView listView = (ListView) view.findViewById(R.id.listCoupons);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Coupon coupon = (Coupon) listView.getItemAtPosition(position);
                mListener.onCouponSelected(coupon);
            }
        });
        listView.setAdapter(adapter);
        coupons = ObjectsHelper.getFakeCoupons();
        adapter.addAll(coupons);

        //CRIDA A LA API PER A MOSTRAR TOTS ELS VALS

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
                //
            }
        });
    }
}

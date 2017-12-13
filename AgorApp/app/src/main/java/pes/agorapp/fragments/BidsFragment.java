package pes.agorapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pes.agorapp.JSONObjects.Bid;
import pes.agorapp.R;
import pes.agorapp.adapters.BidsAdapter;

import static pes.agorapp.helpers.ObjectsHelper.getFakeBids;


public class BidsFragment extends Fragment {

    private View mView;
    List<Bid> bids = new ArrayList<>();
    public BidsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bids, container, false);
        inflateBids();
        return mView;
    }

    private void inflateBids() {
        final BidsAdapter adapter = new BidsAdapter(getActivity(), bids);
        final ListView listView = (ListView) mView.findViewById(R.id.listViewBids);
        listView.setAdapter(adapter);
        adapter.addAll(getFakeBids());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

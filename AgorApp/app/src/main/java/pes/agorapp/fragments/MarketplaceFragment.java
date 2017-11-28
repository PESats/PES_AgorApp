package pes.agorapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import pes.agorapp.R;

/**
 * Created by marc on 6/11/17.
 */

public class MarketplaceFragment extends Fragment implements View.OnClickListener {

    private Button marketplace_publish;
    private Dialog dialogForm;

    public MarketplaceFragment() {}

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_marketplace, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        Button publishButton = (Button) dialogForm.findViewById(R.id.btn_marketplace_publish);

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}

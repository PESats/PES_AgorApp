package pes.agorapp.customComponents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import pes.agorapp.R;

/**
 * Created by marc on 16/10/17.
 */

public class DialogServerKO extends AlertDialog.Builder {

    public DialogServerKO(final Activity context) {
        super(context);
        setTitle(R.string.dialog_server_ko_title);
        setMessage(R.string.dialog_server_ko_message);
        setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        });
    }
}

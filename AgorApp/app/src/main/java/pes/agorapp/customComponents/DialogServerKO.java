package pes.agorapp.customComponents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by marc on 16/10/17.
 */

public class DialogServerKO extends AlertDialog.Builder {

    public DialogServerKO(final Activity context) {
        super(context);
        setTitle("Informació");
        setMessage("No es pot accedir al servidor :(");
        setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.finish();
            }
        });
    }
}

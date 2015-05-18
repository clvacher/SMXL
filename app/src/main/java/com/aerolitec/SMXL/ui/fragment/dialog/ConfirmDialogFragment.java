package com.aerolitec.SMXL.ui.fragment.dialog;

/**
 * Created by gautierragueneau on 23/05/2014.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;


/**
 * Created by Kevin on 11/02/14.
 */
public class ConfirmDialogFragment extends DialogFragment {

    public interface ConfirmDialogListener {
        public void onConfirm(boolean confirmed, int id);
    }

    private ConfirmDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ConfirmDialogListener) activity;
        } catch (ClassCastException e) {
            Log.d("ConfirmDialog", activity.toString()
                    + " should implement ConfirmDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(getTargetFragment() != null) {
            listener = (ConfirmDialogListener) getTargetFragment();
        }

        String message = "";
        String confirm = "";
        int objectId = 0;
        if(getArguments() != null) {
            message = getArguments().getString("message");
            confirm = getArguments().getString("confirm");
            objectId = getArguments().getInt("id");
            if(message == null) {
                message = "Etes-vous sûr ?";
            }
            if(confirm == null) {
                confirm = "Confirmer";
            }
        }

        final int objectIdFinal = objectId;

        builder.setMessage(message)
                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(listener != null)
                            listener.onConfirm(true, objectIdFinal);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });

        return builder.create();
    }
}

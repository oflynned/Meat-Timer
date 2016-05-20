package com.glassbyte.meattimer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by ed on 20/05/16.
 */
@SuppressLint("ValidFragment")
public class MeatDialog extends DialogFragment {

    private String title;
    private setMeatListener meatDialog = null;

    public MeatDialog(String title) {
        this.title = title;
    }

    public interface setMeatListener {
        void onCookClick(DialogFragment dialogFragment);
    }

    public void setMeatListener(setMeatListener meatDialog) {
        this.meatDialog = meatDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle(title)
                .setPositiveButton("Cook!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            meatDialog.onCookClick(MeatDialog.this);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return alertDialog.create();
    }
}

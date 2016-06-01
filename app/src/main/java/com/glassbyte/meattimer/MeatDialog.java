package com.glassbyte.meattimer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.*;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ed on 20/05/16.
 */

//TODO add weight field input

@SuppressLint("ValidFragment")
public class MeatDialog extends DialogFragment {

    private String title;
    private boolean hasDoneness;
    private setMeatListener meatDialog = null;
    private Context context;

    private final int SMALL_MARGIN = 8;
    private final int MEDIUM_MARGIN = 16;
    private final int LARGE_MARGIN = 24;
    private final int XL_MARGIN = 32;
    RelativeLayout layout;
    TextView expTimeLabel, expTimeValue, weightLabel, doneLabel;
    EditText weightEntry;

    String expTime;
    float weight;

    public MeatDialog(String title, boolean hasDoneness) {
        this.title = title;
        this.hasDoneness = hasDoneness;
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
        context = this.getActivity();
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

        RelativeLayout dialogLayout = new RelativeLayout(context);
        RelativeLayout.LayoutParams dialogLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogLayout.setGravity(Gravity.CENTER_VERTICAL);
        dialogLayout.setPadding(Helpers.getDp(context, LARGE_MARGIN), LARGE_MARGIN, 0, 0);
        dialogLayout.setLayoutParams(dialogLayoutParams);

        generateExpView();
        generateWeightView();

        dialogLayout.addView(expTimeLabel);
        dialogLayout.addView(expTimeValue);
        dialogLayout.addView(weightLabel);
        dialogLayout.addView(weightEntry);
        if (hasDoneness) {
            //dialogLayout.addView();
            //dialogLayout.addView();
        }

        alertDialog.setView(dialogLayout);

        return alertDialog.create();
    }

    private void generateExpView() {
        expTimeLabel = new TextView(context);
        RelativeLayout.LayoutParams expTimeLabelParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        expTimeLabelParams.addRule(RelativeLayout.ALIGN_START, RelativeLayout.TRUE);
        expTimeLabel.setText("Expected End");
        expTimeLabel.setId(ViewGroup.generateViewId());

        expTimeValue = new TextView(context);
        RelativeLayout.LayoutParams expTimeParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        expTimeValue.setText(Timings.formatTime(System.currentTimeMillis(), true));
        expTimeParams.addRule(RelativeLayout.RIGHT_OF, expTimeLabel.getId());
        expTimeValue.setLayoutParams(expTimeParams);
        expTimeValue.setId(View.generateViewId());
    }

    private void generateWeightView() {
        weightLabel = new TextView(context);
        RelativeLayout.LayoutParams weightLabelParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        weightLabelParams.addRule(RelativeLayout.BELOW, expTimeLabel.getId());
        weightLabelParams.addRule(RelativeLayout.ALIGN_START, RelativeLayout.TRUE);
        weightLabel.setText("Weight");
        weightLabel.setId(ViewGroup.generateViewId());

        weightEntry = new EditText(context);
        RelativeLayout.LayoutParams weightParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        weightEntry.setHint("g");
        weightParams.addRule(RelativeLayout.RIGHT_OF, weightLabel.getId());
        weightEntry.setLayoutParams(weightParams);
        weightEntry.setId(View.generateViewId());

        /*TextView weightUnits = new TextView(context);
        RelativeLayout.LayoutParams weightUnitsParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        weightUnits.*/
    }

    private void generateDonenessView() {

    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }
}

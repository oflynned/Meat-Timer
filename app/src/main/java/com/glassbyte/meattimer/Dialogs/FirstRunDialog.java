package com.glassbyte.meattimer.Dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.glassbyte.meattimer.Services.Helpers;
import com.glassbyte.meattimer.Services.Manager;

/**
 * Created by ed on 20/05/16.
 */

//TODO add weight field input

@SuppressLint("ValidFragment")
public class FirstRunDialog extends DialogFragment {

    private setFirstRunListener firstRunListener = null;
    private Context context;

    TextView weightLabel;
    RadioGroup radioGroup;

    AlertDialog.Builder alertDialog;

    public FirstRunDialog(Context context) {
        this.context = context;
    }

    public interface setFirstRunListener {
        void onClick(DialogFragment dialogFragment);
    }

    public void setFirstRunListener(setFirstRunListener firstRunListener) {
        this.firstRunListener = firstRunListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        context = this.getActivity();
        alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Units Selection")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firstRunListener.onClick(FirstRunDialog.this);
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
        dialogLayout.setPadding(Helpers.getDp(context, Helpers.LARGE_MARGIN), Helpers.LARGE_MARGIN, 0, 0);
        dialogLayout.setLayoutParams(dialogLayoutParams);

        generateChoiceView();

        dialogLayout.addView(weightLabel);
        dialogLayout.addView(radioGroup);

        alertDialog.setView(dialogLayout);

        return alertDialog.create();
    }

    private void generateChoiceView() {
        weightLabel = new TextView(context);
        RelativeLayout.LayoutParams expTimeLabelParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        expTimeLabelParams.addRule(RelativeLayout.ALIGN_START, RelativeLayout.TRUE);
        weightLabel.setText("Please select your preferred unit of weight. This can be changed later under the settings section.");
        weightLabel.setId(ViewGroup.generateViewId());

        radioGroup = new RadioGroup(getContext());
        RadioButton metricUnits = new RadioButton(getContext());
        metricUnits.setText("Metric");
        RadioButton ukUnits = new RadioButton(getContext());
        ukUnits.setText("UK Imperial");
        RadioButton usUnits = new RadioButton(getContext());
        usUnits.setText("US Imperial");

        RelativeLayout.LayoutParams radioParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioParams.addRule(RelativeLayout.BELOW, weightLabel.getId());
        radioGroup.setLayoutParams(radioParams);
        radioGroup.addView(metricUnits);
        radioGroup.addView(ukUnits);
        radioGroup.addView(usUnits);
        radioGroup.setId(View.generateViewId());
    }

    @Override
    public Context getContext() {
        return context;
    }

    public String getUnitSelected() {
        return ((RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
    }
}

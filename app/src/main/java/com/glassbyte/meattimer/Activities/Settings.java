package com.glassbyte.meattimer.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AlertDialog;

import com.glassbyte.meattimer.R;
import com.glassbyte.meattimer.Services.Manager;

/**
 * Created by ed on 24/01/16.
 */
public class Settings extends PreferenceActivity {
    PreferenceScreen preferenceScreen;

    @SuppressLint("ValidFragment")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setTheme(R.style.AppTheme);

                preferenceScreen = getPreferenceManager().createPreferenceScreen(getActivity());

                PreferenceCategory unitCategory = new PreferenceCategory(getActivity());
                unitCategory.setTitle("Unit Type");

                Preference units = new Preference(getActivity());
                units.setTitle("Units");
                units.setSummary(Manager.getInstance().getPreference(Manager.Pref.Units, Manager.PrefValue.Metric, getActivity()));
                units.setKey(Manager.Pref.Units.name());

                //TODO change on click to allow changing of units
                units.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Preferred Units")
                                .setMessage(Manager.getInstance().getPreference(Manager.Pref.Units, Manager.PrefValue.Metric, getActivity()))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();
                        return false;
                    }
                });

                preferenceScreen.addPreference(unitCategory);
                unitCategory.addPreference(units);
                setPreferenceScreen(preferenceScreen);
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
            }
        }).commit();
    }
}

package com.glassbyte.meattimer.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.test.suitebuilder.annotation.Suppress;

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

                Preference units = new Preference(getActivity());
                units.setTitle("Units");
                units.setSummary(Manager.getInstance().getPreference(Manager.Pref.Units, getActivity()));
                units.setKey(Manager.Pref.Units.name());

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

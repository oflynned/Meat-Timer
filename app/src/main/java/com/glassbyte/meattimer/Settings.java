package com.glassbyte.meattimer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.test.suitebuilder.annotation.Suppress;

/**
 * Created by ed on 24/01/16.
 */
public class Settings extends PreferenceActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    PreferenceScreen preferenceScreen;

    //more apps, pls rate
    //units - metric, US imperial, UK imperial
    //temperature - fahrenheit, celsius, kelvin
    //language - english, irish

    @SuppressLint("ValidFragment")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment() {
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setTheme(R.style.AppTheme);

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                preferenceScreen = getPreferenceManager().createPreferenceScreen(getActivity());

                PreferenceCategory units, languageSettings;
            }

            @Override
            public void onActivityCreated(Bundle savedInstanceState) {
                super.onActivityCreated(savedInstanceState);
            }
        }).commit();
    }

    public Intent getOpenAppIntent(Context context, String app, String linkApp, String linkWeb) {
        try {
            context.getPackageManager().getPackageInfo(app, 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse(linkApp));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse(linkWeb));
        }
    }
}

package com.glassbyte.meattimer.Services;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by ed on 25/08/16.
 */
public class Manager {
    public enum Pref {
        FirstRun, Units
    }

    public enum PrefValue {
        True, False,
        US, UK, Metric,
        Undef
    }

    private static Manager manager;
    public static Manager getInstance() {
        manager = manager == null ? new Manager() : manager;
        return manager;
    }

    public String getPreference(Pref pref, PrefValue defaultPref, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(pref.name(), defaultPref.name());
    }

    public void setPreference(Pref pref, String value, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(pref.name(), value).apply();
    }

    public PrefValue convertInput(String string) {
        for(PrefValue prefValue : PrefValue.values()) {
            if (prefValue.name().equals(string))
                return prefValue;
        }
        return PrefValue.Undef;
    }
}

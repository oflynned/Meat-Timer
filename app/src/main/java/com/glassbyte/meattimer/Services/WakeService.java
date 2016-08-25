package com.glassbyte.meattimer.Services;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ed on 01/02/16.
 */
public class WakeService extends IntentService {

    public WakeService() {
        super("WakeService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //check if to sound alarm?
    }

    public static boolean isServiceRunning(Class<?> serviceClass, Activity activity) {
        ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

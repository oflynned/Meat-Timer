package com.glassbyte.meattimer.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ed on 01/02/16.
 */
public class WakeBootReceiver extends BroadcastReceiver {
    WakeAlarmReceiver wakeAlarmReceiver = new WakeAlarmReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
            wakeAlarmReceiver.setAlarm(context);
    }
}

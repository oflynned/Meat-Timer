package com.glassbyte.meattimer;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.Calendar;

/**
 * Created by ed on 01/02/16.
 */
public class WakeAlarmReceiver extends WakefulBroadcastReceiver {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        startWakefulService(context, new Intent(context, WakeService.class));
    }

    public void setAlarm(Context context) {
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, 0,
                new Intent(context, WakeService.class), 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + Timings.ONE_MINUTE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                Timings.ONE_MINUTE, pendingIntent);

        //automatically restart service if started on system reboot
        ComponentName componentName = new ComponentName(context, WakeService.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context) {
        if(alarmManager!=null)
            alarmManager.cancel(pendingIntent);

        //disable boot receiver so that there is no reset of alarm at boot
        ComponentName componentName = new ComponentName(context, WakeService.class);
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }
}

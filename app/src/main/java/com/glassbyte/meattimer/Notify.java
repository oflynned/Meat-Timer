package com.glassbyte.meattimer;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;

import java.io.Serializable;

/**
 * Created by ed on 24/01/16.
 */
public class Notify implements Serializable {
    private static final int ID = 1;
    private static final int SHORT = 500;
    private static final int MEDIUM = 1000;
    private static final int LONG = 1500;

    Context context;
    NotificationCompat.Builder mBuilder;
    NotificationManager notificationManager;

    public Notify(Context context) {
        this.context = context;
    }

    enum Notification {
        SET, FLIP, DONE
    }

    public void createNotification(String duration, String meat, Notification notification) {
        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_meat_timer)
                .setLargeIcon(Helpers.decodeSampledBitmapFromResource(context.getResources(), R.drawable.ic_launcher, 512, 512));
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        switch (notification) {
            case SET:
                vibrate(SHORT);
                mBuilder.setContentTitle(meat + " (" + duration + ")")
                        .setContentText("A timer has been set going, your device will be notified when it's time to flip or is ready.");
                break;
            case FLIP:
                vibrate(MEDIUM);
                mBuilder.setContentTitle(meat + " (" + duration + ")")
                        .setContentText("Flip your meat so that it's cooked evenly throughout.");
                break;
            case DONE:
                vibrate(LONG);
                mBuilder.setContentTitle(meat + " (" + duration + ")")
                        .setContentText("It's time to remove this from your cooking appliance! Do it soon to make sure it isn't burnt");
                break;
        }

        notificationManager.notify(ID, mBuilder.build());
    }

    public void updateNotification(String duration, String meat) {
        mBuilder.setContentTitle(meat + " (" + duration + ")");
        notificationManager.notify(ID, mBuilder.build());
    }

    public void updateNotification(String duration, String meat, Notification notification) {
        vibrate(LONG);
        mBuilder.setContentTitle(meat + " (" + duration + ")");
        notificationManager.notify(ID, mBuilder.build());
    }

    public void vibrate(int length) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(length);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

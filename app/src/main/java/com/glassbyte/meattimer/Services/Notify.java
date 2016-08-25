package com.glassbyte.meattimer.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.glassbyte.meattimer.Activities.Timer;
import com.glassbyte.meattimer.R;

/**
 * Created by ed on 24/01/16.
 */
public class Notify {
    private static final int ID = 1, SHORT = 500, MEDIUM = 1000, LONG = 1500;
    private final int WIDTH = 256, HEIGHT = 256;

    Context context;
    NotificationCompat.Builder mBuilder;
    NotificationManager notificationManager;
    PendingIntent pendingIntent;

    public Notify(Context context) {
        this.context = context;
    }

    enum Notification {
        SET, FLIP, DONE
    }

    public void createNotification() {
        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_meat_timer)
                .setLargeIcon(Helpers.decodeSampledBitmapFromResource(context.getResources(),
                        R.drawable.ic_launcher, WIDTH, HEIGHT));
        pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, Timer.class), PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID, mBuilder.build());
        vibrate(SHORT);
    }

    public void updateNotification(String duration, String meat) {
        mBuilder.setContentTitle(meat + " (" + duration + ")")
                .setContentText(duration + " remaining until done!");
        notificationManager.notify(ID, mBuilder.build());
    }

    public void updateNotification(String duration, String meat, Notification notification) {
        switch (notification) {
            case SET:
                mBuilder.setContentTitle(meat + " is cooking! (" + duration + ")");
                break;
            case FLIP:
                vibrate(MEDIUM);
                mBuilder.setContentText("Flip your " + meat + "!");
                break;
            case DONE:
                vibrate(LONG);
                mBuilder.setContentTitle(meat + " is done!")
                        .setContentText("Take your meat out of your appliance!");
                break;
        }
        mBuilder.setContentTitle(meat + " (" + duration + ")");
        notificationManager.notify(ID, mBuilder.build());
    }

    public void cancelNotifications() {
        notificationManager.cancel(ID);
    }

    public void vibrate(int length) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(length);
    }
}

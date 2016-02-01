package com.glassbyte.meattimer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by ed on 24/01/16.
 */
public class Notify {

    private static final int ID = 1;

    private Context context;
    private boolean isNotified;
    private PendingIntent pendingIntent;
    private NotificationCompat.Builder notification;
    private Vibrator vibrator;

    public Notify(Context context){this.context=context;}

    public void createNotification(){
        notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(getIcon(context))
                .setContentTitle("Notification title")
                .setContentText("Notification text")
                .setContentIntent(PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT));

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID, notification.build());
    }

    private void createDialogNotification(String meat){
        vibrate();
        new AlertDialog.Builder(context)
                .setTitle("Your meat is cooked!")
                .setMessage("(" + meat + ")")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private static Bitmap getIcon(Context context){
        return BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
    }

    public void soundAlarm(){

    }

    public void vibrate(){
        vibrator.vibrate(1500);
    }
}

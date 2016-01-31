package com.glassbyte.meattimer;

import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;

/**
 * Created by ed on 24/01/16.
 */
public class Notify {

    private Context context;
    private boolean isNotified;

    public Notify(Context context){this.context=context;}

    public void createNotification(){
        Notification notification = new NotificationCompat.Builder(context);

    }

    private void createDialogNotification(){
        new AlertDialog.Builder(context)
                .setTitle("Your meat is cooked!")
                .setMessage("")
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

    public void soundAlarm(){}
    public void vibrate(){}
    public void dismiss(){}
}

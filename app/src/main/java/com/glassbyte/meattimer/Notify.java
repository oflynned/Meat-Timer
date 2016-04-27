package com.glassbyte.meattimer;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;

/**
 * Created by ed on 24/01/16.
 */
public class Notify {
    private static final int SHORT = 500;
    private static final int MEDIUM = 1000;
    private static final int LONG = 1500;

    private Context context;
    Vibrator vibrator;

    public Notify(Context context){
        this.context=context;
    }

    public void createNotification(){

    }

    private void createDialogNotification(String meat){
        vibrate();
        new AlertDialog.Builder(context)
                .setTitle("Time's up!")
                .setMessage("Your meat is cooked! (" + meat + ")")
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

    public void vibrate(){
        vibrator.vibrate(LONG);
    }
}

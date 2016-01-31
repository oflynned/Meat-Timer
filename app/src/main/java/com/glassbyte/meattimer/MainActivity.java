package com.glassbyte.meattimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {

    final int duration = (int) (2 * Timings.ONE_SECOND);
    final float fishThickness = 0.1f;
    CircularProgressBar circularProgressBar;
    TextView timeRemaining;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        circularProgressBar = (CircularProgressBar) findViewById(R.id.timer);
        timeRemaining = (TextView) findViewById(R.id.time_remaining);

        setInitialProgress();

        countDownTimer = new CountDownTimer(Timings.getFishTime(fishThickness), Timings.ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining.setText(Timings.formatTime(millisUntilFinished, true) + " remaining");
                circularProgressBar.setProgress(getProgress(millisUntilFinished, Timings.getFishTime(fishThickness)));
            }

            @Override
            public void onFinish() {
                timeRemaining.setText("Done!");
                setFinalProgress();
            }
        };

        countDownTimer.start();
    }

    private void setInitialProgress(){
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.blue500));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.blue100));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progress_bar_width));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.background_progress_bar_width));
        circularProgressBar.setProgressWithAnimation(100, duration);
    }

    private void setFinalProgress(){
        circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        circularProgressBar.setProgressWithAnimation(100, duration);
    }

    public static float getProgress(long timeRemaining, long totalTime){
        return (((float) timeRemaining / (float) totalTime) * 100);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

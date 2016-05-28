package com.glassbyte.meattimer;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

/**
 * Created by ed on 25/04/16.
 */
public class Timer extends AppCompatActivity {
    private static final int DURATION = (int) (2 * Timings.ONE_SECOND);

    CircularProgressBar circularProgressBar;
    TextView timeRemaining;
    CountDownTimer countDownTimer;

    Notify notify = new Notify(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        circularProgressBar = (CircularProgressBar) findViewById(R.id.timer);
        timeRemaining = (TextView) findViewById(R.id.time_remaining);

        setInitialProgress();

        final String meat = getIntent().getStringExtra("MEAT");
        final long totalTime = getIntent().getLongExtra("TIME", 0);
        final boolean isFlip = getIntent().getBooleanExtra("FLIP", false);

        notify.createNotification(Timings.formatTime(totalTime), meat, Notify.Notification.SET);

        countDownTimer = new CountDownTimer(totalTime, Timings.ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeLeft = Timings.formatTime(millisUntilFinished, true);
                notify.updateNotification(timeLeft, meat);
                timeRemaining.setText(getString(R.string.timer_time_remaining, meat, timeLeft));
                circularProgressBar.setProgress(getProgress(millisUntilFinished, totalTime));

                if(isFlip) {
                    if(millisUntilFinished >= totalTime / 2 - Timings.ONE_SECOND &&
                            millisUntilFinished <= totalTime / 2 + Timings.ONE_SECOND) {
                        notify.updateNotification(Timings.formatTime(millisUntilFinished), meat);
                    }
                }
            }

            @Override
            public void onFinish() {
                timeRemaining.setText("Done!");
                notify.updateNotification("Done!", meat, Notify.Notification.DONE);
                setFinalProgress();
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(Timer.this, MainActivity.class));
        finish();
    }

    /**
     * Set the countdown circle to 100% with respect to total countdown time and set
     * the theme of the countdown circle to be blue to indicate cooking
     */
    private void setInitialProgress(){
        circularProgressBar.setColor(ContextCompat.getColor(this, R.color.blue500));
        circularProgressBar.setBackgroundColor(ContextCompat.getColor(this, R.color.blue100));
        circularProgressBar.setProgressBarWidth(getResources().getDimension(R.dimen.progress_bar_width));
        circularProgressBar.setBackgroundProgressBarWidth(getResources().getDimension(R.dimen.background_progress_bar_width));
        circularProgressBar.setProgressWithAnimation(100, DURATION);
    }

    /**
     * Reset countdown circle to 100% on finishing and change colour to red to indicate done
     */
    private void setFinalProgress(){
        circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        circularProgressBar.setProgressWithAnimation(100, DURATION);
    }

    /**
     * Returns the current percentage progress of the cooking
     * @param timeRemaining time left until done
     * @param totalTime total time to elapse until done
     * @return the percentage elapsed of doneness
     */
    public static float getProgress(long timeRemaining, long totalTime){
        return (((float) timeRemaining / (float) totalTime) * 100);
    }
}

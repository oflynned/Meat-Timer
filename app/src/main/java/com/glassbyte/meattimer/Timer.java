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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        circularProgressBar = (CircularProgressBar) findViewById(R.id.timer);
        timeRemaining = (TextView) findViewById(R.id.time_remaining);

        setInitialProgress();
        Bundle bundle = getIntent().getExtras();
        final long totalTime = bundle.getLong("TIME");

        countDownTimer = new CountDownTimer(totalTime, Timings.ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                String timeLeft = Timings.formatTime(millisUntilFinished, true);
                timeRemaining.setText(timeLeft + " remaining");
                circularProgressBar.setProgress(getProgress(millisUntilFinished, totalTime));
            }

            @Override
            public void onFinish() {
                timeRemaining.setText("Done!");
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

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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends AppCompatActivity {

    final int duration = (int) (2 * Timings.ONE_SECOND);
    CircularProgressBar circularProgressBar;
    TextView timeRemaining;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Meat fish = new Meat(2.0f, "Fish");
        Meat chicken = new Meat(1600, "Chicken");
        Meat beef = new Meat(1000, "MEDIUM", "Beef");




        System.out.println(fish.getType() + " (" + fish.getThickness() + " cm thick) must be cooked for " +
                Timings.formatTime(Timings.getFishTime(fish.getThickness()), true));
        System.out.println(chicken.getType() + " (" + WeightConversion.gToKg(chicken.getWeight()) + " kg) must be cooked for " +
                Timings.formatTime(Timings.getRoastChickenTime(chicken.getThickness()), true));
        System.out.println(beef.getType() + " (" + WeightConversion.gToKg(beef.getWeight()) + " kg) must be cooked for " +
                Timings.formatTime(Timings.getRoastBeefTime(beef.getWeight(), beef.getDoneness()), true));
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
        circularProgressBar.setProgressWithAnimation(100, duration);
    }

    /**
     * Reset countdown circle to 100% on finishing and change colour to red to indicate done
     */
    private void setFinalProgress(){
        circularProgressBar.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        circularProgressBar.setProgressWithAnimation(100, duration);
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

    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    private class Meat {
        long weight;
        float thickness;
        String doneness, type;

        public Meat(long weight, String type){
            this.weight = weight;
            this.type = type;
        }

        public Meat(long weight, String doneness, String type) {
            this.weight = weight;
            this.doneness = doneness;
            this.type = type;
        }

        public Meat(float thickness, String type) {
            this.thickness = thickness;
            this.type = type;
        }

        public long getWeight(){return this.weight;}
        public float getThickness(){return this.thickness;}
        public String getDoneness(){return this.doneness;}
        public String getType(){return this.type;}
    }
}

/*
        circularProgressBar = (CircularProgressBar) findViewById(R.id.timer);
        timeRemaining = (TextView) findViewById(R.id.time_remaining);

        setInitialProgress();
        final long totalTime = Timings.getFishTime(3);

        countDownTimer = new CountDownTimer(totalTime, Timings.ONE_SECOND) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemaining.setText(Timings.formatTime(millisUntilFinished, true) + " remaining");
                circularProgressBar.setProgress(getProgress(millisUntilFinished, totalTime));
            }

            @Override
            public void onFinish() {
                timeRemaining.setText("Done!");
                setFinalProgress();
            }
        };
        countDownTimer.start();*/

package com.glassbyte.meattimer;

import java.util.concurrent.TimeUnit;

/**
 * Created by ed on 14/12/15.
 */
public class Timings {

    //timing nomenclatures
    public static final long ONE_SECOND = 1000;
    public static final long THIRTY_SECONDS = ONE_SECOND * 30;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long FIFTEEN_MINUTES = ONE_MINUTE * 15;
    public static final long THIRTY_MINUTES = ONE_MINUTE * 30;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long TWO_HOURS = ONE_HOUR * 2;

    //recommended times

    //steak
    //fillet - 4.5-5cm thick
    public static final long FILLET_STEAK_RARE = 3 * ONE_MINUTE + THIRTY_SECONDS;
    public static final long FILLET_STEAK_MEDIUM_RARE = 4 * ONE_MINUTE + THIRTY_SECONDS;
    public static final long FILLET_STEAK_MEDIUM = 5 * ONE_MINUTE + THIRTY_SECONDS;
    public static final long FILLET_STEAK_WELL_DONE = 7 * ONE_MINUTE;
    //strip-loin/rib-eye/sirloin - 2-3cm thick
    public static final long STRIP_LOIN_RARE = 3 * ONE_MINUTE;
    public static final long STRIP_LOIN_MEDIUM_RARE = 3 * ONE_MINUTE + THIRTY_SECONDS;
    public static final long STRIP_LOIN_MEDIUM = 4 * ONE_MINUTE + THIRTY_SECONDS;
    public static final long STRIP_LOIN_WELL_DONE = 6 * ONE_MINUTE;
    //t-bone/cowboy - 3cm thick
    public static final long T_BONE_RARE = 4 * ONE_MINUTE + THIRTY_SECONDS;
    public static final long T_BONE_MEDIUM_RARE = 5 * ONE_MINUTE + THIRTY_SECONDS;
    public static final long T_BONE_MEDIUM = 7 * ONE_MINUTE;
    public static final long T_BONE_WELL_DONE = 9 * ONE_MINUTE;

    //chicken
    //oven
    public static final long CHICKEN_OVEN_WINGS_THIGHS = 40 * ONE_MINUTE;
    public static final long CHICKEN_OVEN_BREAST = 15 * ONE_MINUTE;
    //pan
    public static final long CHICKEN_PAN_BREAST = 20 * ONE_MINUTE;
    //grill/bbq
    public static final long CHICKEN_BBQ_BREAST = 10 * ONE_MINUTE;
    public static final long CHICKEN_BBQ_STRIPS = 7 * ONE_MINUTE;
    public static final long CHICKEN_BBQ_DRUMSTICKS_THIGHS = 30 * ONE_MINUTE;
    public static final long CHICKEN_BBQ_WINGS = 40 * ONE_MINUTE;

    //beef
    //burgers
    public static final long BEEF_BURGERS = 15 * ONE_MINUTE;

    //lamb
    public static final long LAMB_CHOPS = 7 * ONE_MINUTE;
    public static final long LAMB_LOIN = 30 * ONE_MINUTE;
    public static final long LAMB_BURGERS = 20 * ONE_MINUTE;

    //coefficients of cooking weights in g or thickness
    public static final float CHICKEN_COEFF = 500f; //g
    public static final float ROAST_BEEF_COEFF = 450f; //g
    public static final float FISH_COEFF = 2.5f; //cm

    public static long getNumberOfMinsInMillis(int mins){
        return mins * ONE_MINUTE;
    }

    public static long getMillisInMinutes(long millis){
        return TimeUnit.MILLISECONDS.toMinutes(millis);
    }

    public static long getMillisInHours(long millis){
        return TimeUnit.MILLISECONDS.toHours(millis);
    }

    public static String formatTimeWithSeconds(long millis, boolean hasSeconds){
        String hours = String.valueOf(((millis / ONE_HOUR) % 24));
        String minutes = String.valueOf(((millis / ONE_MINUTE) % 60));
        String seconds = String.valueOf(((millis / ONE_SECOND) % 60));

        if(Integer.parseInt(minutes) < 10)
            minutes = "0" + minutes;

        if(Integer.parseInt(seconds) < 10)
            seconds = "0" + seconds;

        if(hasSeconds)
            return hours + ":" + minutes + ":" + seconds;

        return hours + ":" + minutes;
    }

    public static long getRoastChickenTime(float weight){
        return (long) ((ONE_MINUTE * 25) * Math.floor(weight / CHICKEN_COEFF) +
                (ONE_MINUTE * 25) * ((weight % CHICKEN_COEFF) / CHICKEN_COEFF) +
                (25 * ONE_MINUTE));
    }

    public static long getRoastBeefTime(float weight, String rareness){
        long time = 0;
        switch(rareness){
            case "RARE":
                time = (long) ((20 * ONE_MINUTE) +
                        15 * Math.floor(weight / ROAST_BEEF_COEFF) +
                        15 * ((weight % ROAST_BEEF_COEFF) / ROAST_BEEF_COEFF));
                break;
            case "MEDIUM":
                time = (long) ((20 * ONE_MINUTE) +
                        15 * Math.floor(weight / ROAST_BEEF_COEFF) +
                        15 * ((weight % ROAST_BEEF_COEFF) / ROAST_BEEF_COEFF) +
                        15 * ONE_MINUTE);
                break;
            case "WELL DONE":
                time = (long) ((20 * ONE_MINUTE) +
                        15 * Math.floor(weight / ROAST_BEEF_COEFF) +
                        15 * ((weight % ROAST_BEEF_COEFF) / ROAST_BEEF_COEFF) +
                        30 * ONE_MINUTE);
                break;
        }
        return time;
    }

    public static long getFishTime(float thickness) {
        if (thickness > FISH_COEFF)
            return (long) (10 * ONE_MINUTE * (Math.floor(thickness / FISH_COEFF) + (thickness % FISH_COEFF) / FISH_COEFF));
        else return (long) (10 * (thickness / FISH_COEFF));
    }
}

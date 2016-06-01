package com.glassbyte.meattimer;

import java.util.concurrent.TimeUnit;

/**
 * Created by ed on 14/12/15.
 */

//TODO fix roast chicken algorithm (overestimation)
//TODO fix roast beef algorithm (underestimation)
//TODO add roast lamb implementation

public class Timings {

    public enum Doneness {
        RARE, MEDIUM, WELL
    }

    //timing nomenclatures
    public static final long ONE_SECOND = 1000;
    public static final long THIRTY_SECONDS = ONE_SECOND * 30;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;

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
    public static final long CHICKEN_BBQ_DRUMSTICKS_THIGHS = 15 * ONE_MINUTE;
    public static final long CHICKEN_BBQ_WINGS = 20 * ONE_MINUTE;

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
    public static final float TURKEY_COEFF = 1000f; //g
    public static final float HAM_COEFF = 450f; //g

    /**
     * Convert mins to millis
     * @param mins to be converted
     * @return millis representation of mins
     */
    public static long getNumberOfMinsInMillis(int mins){
        return mins * ONE_MINUTE;
    }

    /**
     * Convert millis to mins
     * @param millis to be converted
     * @return minute representation of millis
     */
    public static long getMillisInMinutes(long millis){
        return TimeUnit.MILLISECONDS.toMinutes(millis);
    }

    /**
     * Converts millis to hours
     * @param millis millis to be converted
     * @return hour representation of millis
     */
    public static long getMillisInHours(long millis){
        return TimeUnit.MILLISECONDS.toHours(millis);
    }

    /**
     * Formats a millis representation to that of hh:mm:ss
     * @param millis millis to elapse
     * @param hasSeconds boolean should format seconds
     * @return representation of millis in given format
     */
    public static String formatTime(long millis, boolean hasSeconds){
        String hours = String.valueOf(((millis / ONE_HOUR) % 24));
        String minutes = String.valueOf(((millis / ONE_MINUTE) % 60));
        String seconds = String.valueOf(((millis / ONE_SECOND) % 60));

        if(Integer.parseInt(hours) < 10)
            hours = "0" + hours;

        if(Integer.parseInt(minutes) < 10)
            minutes = "0" + minutes;

        if(Integer.parseInt(seconds) < 10)
            seconds = "0" + seconds;

        if(hasSeconds)
            return hours + ":" + minutes + ":" + seconds;

        return hours + ":" + minutes;
    }

    /**
     * Formats a millis representation to that of hh:mm:ss
     * @param millis millis to elapse
     * @return representation of millis in given format
     */
    public static String formatTime(long millis){
        String hours = String.valueOf((millis / ONE_HOUR) % 24);
        String minutes = String.valueOf((millis / ONE_MINUTE) % 60);
        String seconds = String.valueOf((millis / ONE_SECOND) % 60);

        if(Integer.parseInt(hours) < 10)
            hours = "0" + hours;

        if(Integer.parseInt(minutes) < 10)
            minutes = "0" + minutes;

        if(Integer.parseInt(seconds) < 10)
            seconds = "0" + seconds;

        if(Integer.parseInt(hours) > 0)
            return hours + ":" + minutes + ":" + seconds;

        return minutes + ":" + seconds;
    }

    /**
     * Returns the time in millis with respect to the weight of the piece
     * of meat until cooked
     * @param weight weight in grams of the whole chicken
     * @return the time in millis to elapse until done
     */
    public static long getRoastChickenTime(float weight){
        return (long) ((ONE_MINUTE * 25) * Math.floor(weight / CHICKEN_COEFF) +
                (ONE_MINUTE * 25) * ((weight % CHICKEN_COEFF) / CHICKEN_COEFF) +
                (25 * ONE_MINUTE));
    }

    /**
     * Returns the time in millis of cooking time for a roast ham with
     * respect to the weight of the piece of meat
     * @param weight weight in grams of the piece of meat
     * @return the time to elapse until cooked
     */
    public static long getRoastHamTime(float weight){
        return (long) ((ONE_MINUTE * 30) * Math.floor(weight / HAM_COEFF) +
                (ONE_MINUTE * 30) * ((weight % HAM_COEFF) / HAM_COEFF) +
                (30 * ONE_MINUTE));
    }

    /**
     * Returns the time in millis of cooking time for a roast turkey with
     * respect to weight in grams
     * @param weight weight in grams
     * @return the cooking time in millis of a turkey
     */
    public static long getRoastTurkeyTime(float weight){
        if(weight > 4 * TURKEY_COEFF){
            return (long) ((20 * ONE_MINUTE) * Math.floor(weight / TURKEY_COEFF) +
                    ((20 * ONE_MINUTE) * (weight % TURKEY_COEFF) / TURKEY_COEFF) +
                    90 * ONE_MINUTE);
        } else {
            return (long) ((20 * ONE_MINUTE) * Math.floor(weight / TURKEY_COEFF) +
                    ((20 * ONE_MINUTE) * (weight % TURKEY_COEFF) / TURKEY_COEFF) +
                    70 * ONE_MINUTE);
        }
    }

    /**
     * Returns the time in millis of cooking time for roast beef with respect to
     * weight and also the level of rareness
     * @param weight weight in grams of the meat
     * @param rareness rareness of final cooked meat
     * @return cooking time in millis
     */
    public static long getRoastBeefTime(float weight, Doneness rareness){
        switch(rareness){
            case RARE:
                return (long) ((20 * ONE_MINUTE) +
                        15 * Math.floor(weight / ROAST_BEEF_COEFF) +
                        15 * ((weight % ROAST_BEEF_COEFF) / ROAST_BEEF_COEFF));
            case MEDIUM:
                return (long) ((20 * ONE_MINUTE) +
                        15 * Math.floor(weight / ROAST_BEEF_COEFF) +
                        15 * ((weight % ROAST_BEEF_COEFF) / ROAST_BEEF_COEFF) +
                        15 * ONE_MINUTE);
            case WELL:
                return (long) ((20 * ONE_MINUTE) +
                        15 * Math.floor(weight / ROAST_BEEF_COEFF) +
                        15 * ((weight % ROAST_BEEF_COEFF) / ROAST_BEEF_COEFF) +
                        30 * ONE_MINUTE);
            default:
                return -1;
        }
    }

    /**
     * Returns time in millis of fish cooking time via thickness in cm
     * @param thickness the thickness of the fish in cm
     * @return milliseconds to cook for
     */
    public static long getFishTime(float thickness) {
        //thickness in cm
        if (thickness > FISH_COEFF)
            return (long) (10 * ONE_MINUTE * (Math.floor(thickness / FISH_COEFF) + (thickness % FISH_COEFF) / FISH_COEFF));
        else return (long) (10 * ONE_MINUTE * (thickness / FISH_COEFF));
    }
}

package com.glassbyte.meattimer;

/**
 * Created by ed on 24/01/16.
 */
public class WeightConversion {

    private static final float G_KG_COEFF = 0.001f;
    private static final float KG_LB_COEFF = 2.2046f;
    private static final float LB_KG_COEFF = 0.4536f;
    private static final float KG_OZ_COEFF = 35.274f;
    private static final float OZ_KG_COEFF = 0.02835f;

    public static float gToKg(float weight){return weight * G_KG_COEFF;}
    public static float kgTolb(float weight){return weight * KG_LB_COEFF;}
    public static float lbToKg(float weight){return weight * LB_KG_COEFF;}
    public static float kgToOz(float weight){return weight * KG_OZ_COEFF;}
    public static float ozToKg(float weight){return weight * OZ_KG_COEFF;}
}

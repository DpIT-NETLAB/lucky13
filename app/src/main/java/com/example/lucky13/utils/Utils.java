package com.example.lucky13.utils;

import android.util.Log;

import java.text.DecimalFormat;
import java.text.ParseException;

public class Utils {

    public static double fromStringToDouble(String str) throws ParseException {

        DecimalFormat dF = new DecimalFormat("0.00");

        try {
            Number num = dF.parse(str);
            double doubleValue = num.doubleValue();

            return doubleValue;
        } catch (Exception exception) {

            Log.d("Str->Double Conversion", "Given String does not contain valid double value");
        }

        return -1;
    }
}

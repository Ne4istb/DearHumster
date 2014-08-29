package com.ne4istb.dearhamster;

import android.util.Log;

public class UtilsHelper {

    public static final boolean DEBUG = true;

    public static final String LOG_TAG = "HamsterLog";

    public static void printException(Throwable e) {
        if (DEBUG) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    public static void println(String line) {

        if (DEBUG) {
            Log.d(LOG_TAG, line);
        }
    }
}

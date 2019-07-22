package com.gantix.JailMonkey.AdbEnabled;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;


public class AdbEnabled {

    //returns true if mock location enabled, false if not enabled.
    public static boolean AdbEnabled(Context context) {

        Log.i("AdbEnabled", "AdbEnabled: " + (Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) == 1));

        if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) == 1) {
            // ADB_ENABLED enabled
            return true;
        } else {
            // ADB_ENABLED does not enabled
            return false;
        }
    }
}

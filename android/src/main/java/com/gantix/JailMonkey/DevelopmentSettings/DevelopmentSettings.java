package com.gantix.JailMonkey.DevelopmentSettings;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;


public class DevelopmentSettings {

    //returns true if mock location enabled, false if not enabled.
    public static boolean developmentSettings(Context context) {

        Log.i("developmentSettings", "developmentSettings: "+(Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 0));
        if (Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 0) {
            // DEVELOPMENT_SETTINGS_ENABLED enabled
            return true;
        } else {
            //;DEVELOPMENT_SETTINGS_ENABLED not enabled
            return false;
        }
    }
}

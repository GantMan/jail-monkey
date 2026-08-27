package com.gantix.JailMonkey.AdbEnabled;

import android.content.Context;
import android.provider.Settings;


public class AdbEnabled {

    // Returns whether Android Debug Bridge is enabled.
    public static boolean AdbEnabled(Context context) {
        return Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0) == 1;
    }
}

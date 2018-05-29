package com.gantix.JailMonkey.MockLocation;

import android.content.Context;
import android.provider.Settings;

public class MockLocationCheck {

    //returns true if mock location enabled, false if not enabled.
    public static boolean isMockLocationOn(Context context) {
        if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
            return false;
        } else {
            return true;
        }
    }
}

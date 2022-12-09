package com.gantix.JailMonkey.Rooted;

import android.content.Context;

import android.os.Build;

public class RootedCheck {

    /**
     * Checks if the device is rooted.
     *
     * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
     */
    public static boolean isJailBroken(Context context) {
        CheckApiVersion check;

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            check = new GreaterThan23();
        } else {
            check = new LessThan23();
        }
        return check.checkRooted();
    }
}

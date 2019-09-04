package com.gantix.JailMonkey.Rooted;

import android.content.Context;

import com.scottyab.rootbeer.RootBeer;
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
        return check.checkRooted() || rootBeerCheck(context);
    }

    private static boolean rootBeerCheck(Context context) {
        RootBeer rootBeer = new RootBeer(context);
        
        return rootBeer.isRootedWithoutBusyBoxCheck();
    }
}

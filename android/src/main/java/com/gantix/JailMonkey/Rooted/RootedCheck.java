package com.gantix.JailMonkey.Rooted;

import static com.scottyab.rootbeer.Const.BINARY_SU;
import android.content.Context;
import com.scottyab.rootbeer.RootBeer;

public class RootedCheck {

    /**
     * Checks if the device is rooted.
     *
     * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
     */
    public static boolean isJailBroken(Context context) {
        return checkWithJailMonkeyMethod() || rootBeerCheck(context);
    }

    private static boolean checkWithJailMonkeyMethod() {
        CheckApiVersion check;

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            check = new GreaterThan23();
        } else {
            check = new LessThan23();
        }
        return check.checkRooted();
    }

    private static boolean rootBeerCheck(Context context) {
        RootBeer rootBeer = new RootBeer(context);

        return rootBeer.detectRootManagementApps() || rootBeer.detectPotentiallyDangerousApps() || rootBeer.checkForBinary(BINARY_SU)
                || rootBeer.checkForDangerousProps() || rootBeer.checkForRWPaths()
                || rootBeer.detectTestKeys() || rootBeer.checkSuExists() || rootBeer.checkForRootNative() || rootBeer.checkForMagiskBinary();
    }
}

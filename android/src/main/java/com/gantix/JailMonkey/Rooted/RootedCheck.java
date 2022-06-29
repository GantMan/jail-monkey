package com.gantix.JailMonkey.Rooted;

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
        return new RootBeerResults(context).isJailBroken();
    }

    private static class RootBeerResults {
        private final boolean detectRootManagementApps;
        private final boolean detectPotentiallyDangerousApps;
        private final boolean checkForSuBinary;
        private final boolean checkForDangerousProps;
        private final boolean checkForRWPaths;
        private final boolean detectTestKeys;
        private final boolean checkSuExists;
        private final boolean checkForRootNative;
        private final boolean checkForMagiskBinary;

        RootBeerResults(Context context) {
            final RootBeer rootBeer = new RootBeer(context);
            rootBeer.setLogging(false);

            detectRootManagementApps = rootBeer.detectRootManagementApps();
            detectPotentiallyDangerousApps = rootBeer.detectPotentiallyDangerousApps();
            checkForSuBinary = rootBeer.checkForSuBinary();
            checkForDangerousProps = rootBeer.checkForDangerousProps();
            checkForRWPaths = rootBeer.checkForRWPaths();
            detectTestKeys = rootBeer.detectTestKeys();
            checkSuExists = rootBeer.checkSuExists();
            checkForRootNative = rootBeer.checkForRootNative();
            checkForMagiskBinary = rootBeer.checkForMagiskBinary();
        }

        public boolean isJailBroken() {
            return detectRootManagementApps || detectPotentiallyDangerousApps || checkForSuBinary
                    || checkForDangerousProps || checkForRWPaths
                    || detectTestKeys || checkSuExists || checkForRootNative || checkForMagiskBinary;
        }
    }
}

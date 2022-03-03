package com.gantix.JailMonkey.Rooted;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class GreaterThan23 implements CheckApiVersion {

    @Override
    public boolean checkRooted() {
        return checkRootMethod1() || checkRootMethod2() || checkUserIdAndExecuteSu() || checkFolderAccess() || checkInstalls();
    }

    private boolean checkRootMethod1() {
        String[] paths = {
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private boolean checkRootMethod2() {
        Process process = null;
        try {
            final String whichPathOld = "system/xbin/which";
            final String whichPathNew = "system/bin/which";

            if (new File(whichPathOld).exists()) {
                process = Runtime.getRuntime().exec(new String[]{whichPathOld, "su"});
            } else if (new File(whichPathNew).exists()) {
                process = Runtime.getRuntime().exec(new String[]{whichPathNew, "su"});
            } else {
                process = Runtime.getRuntime().exec(new String[]{"which", "su"});
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    private boolean checkUserIdAndExecuteSu() {
        Process process = null;

        try {
            process = Runtime.getRuntime().exec("id");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine().contains("uid=0")) return true;

            // Android Will throw exception if permission denied when user try to invoke Su
            process = Runtime.getRuntime().exec("su");
            return true;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    private boolean checkFolderAccess() {
        String[] paths = {"/data"};
        for (String path : paths) {
            if (new File(path).canRead()) return true;
        }
        return false;
    }

    private boolean checkInstalls() {
        Process process = null;
        String[] installs = {
                "magisk",
                "com.noshufou.androd.au",
                "com.thirdparty.superuser",
                "eu.chainfire.supersu",
                "com.koushikdutta.superuser",
                "com.zachspong.temprootromovejb",
                "com.ramdroid.appquarantine",
                "com.devadvance.rootcloak",
                "com.devadvance.rootcloakplus",
        };

        try {
            process = Runtime.getRuntime().exec("pm list packages");
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = in.readLine();
            while (line != null) {
                for (String keyWord : installs) {
                    if (line.contains(keyWord)) {
                        return true;
                    }
                }
                line = in.readLine();
            }
        } catch (Throwable e) {
            return false;
        }
        return false;
    }
}

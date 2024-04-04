package com.gantix.JailMonkey.Rooted;

import java.io.File;

public class LessThan23 implements CheckApiVersion {
    @Override
    public boolean checkRooted() {
        return canExecuteCommand("/system/xbin/which su") || isSuperuserPresent();
    }

    // executes a command on the system
    private static boolean canExecuteCommand(String command) {
        boolean executeResult;
        try {
            Process process = Runtime.getRuntime().exec(command);
            if (process.waitFor() == 0) {
                executeResult = true;
            } else {
                executeResult = false;
            }
        } catch (Exception e) {
            executeResult = false;
        }

        return executeResult;
    }

    private static boolean isSuperuserPresent() {
        // Check if /system/app/Superuser.apk is present
        String[] paths = {
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"
        };

        for (String path : paths) {
            if (new File(path).exists()) {
                return true;
            }
        }

        return false;
    }
}

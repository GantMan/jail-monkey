package com.gantix.JailMonkey.Rooted;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class GreaterThan23 implements CheckApiVersion {

    @Override
    public boolean checkRooted() {
        return checkRootMethod1() || checkRootMethod2();
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
            process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            if (in.readLine() != null) return true;
            return false;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }
}

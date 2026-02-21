package com.gantix.JailMonkey.HookDetection;

import android.app.ActivityManager;
import android.content.Context;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Arrays;
import java.util.List;

public class HookDetectionCheck {

    /**
     * Detects if there is any suspicious installed application.
     *
     * @return <code>true</code> if some bad application is installed, <code>false</code> otherwise.
     */
    public static boolean hookDetected(Context context) {
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> applicationInfoList = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        String[] dangerousPackages = {
                "de.robv.android.xposed.installer",
                "com.saurik.substrate",
                "de.robv.android.xposed",
                "com.noshufou.android.su.elite",
                "eu.chainfire.supersu",
                "com.koushikdutta.superuser",
                "com.thirdparty.superuser",
                "com.yellowes.su",
                "com.koushikdutta.rommanager",
                "com.koushikdutta.rommanager.license",
                "com.dimonvideo.luckypatcher",
                "com.chelpus.lackypatch",
                "com.ramdroid.appquarantine",
                "com.ramdroid.appquarantinepro",
                "de.robv.android.xposed.installer",
                "com.saurik.substrate",
                "com.zachspong.temprootremovejb",
                "com.amphoras.hidemyroot",
                "com.amphoras.hidemyrootadfree",
                "com.formyhm.hiderootPremium",
                "com.formyhm.hideroot",
                "me.phh.superuser",
                "eu.chainfire.supersu.pro",
                "com.kingouser.com",
                "com.topjohnwu.magisk"
            };

        if (applicationInfoList != null) {
            for (ApplicationInfo applicationInfo : applicationInfoList) {
                if (Arrays.asList(dangerousPackages).contains(applicationInfo.packageName)) {
                    return true;
                }
            }
        }

        return advancedHookDetection(context);
    }

    private static boolean advancedHookDetection(Context context) {
        try {
            throw new Exception();
        } catch (Exception e) {
            int zygoteInitCallCount = 0;
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                if (stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    zygoteInitCallCount++;
                    if (zygoteInitCallCount == 2) {
                        return true;
                    }
                }

                if (stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") &&
                        stackTraceElement.getMethodName().equals("invoked")) {
                    return true;
                }

                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
                        stackTraceElement.getMethodName().equals("main")) {
                    return true;
                }

                if (stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
                        stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    return true;
                }
            }
        }

        return checkFrida(context);
    }

    private static boolean checkFrida(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = activityManager.getRunningServices(300);

        if (runningServices != null) {
            for (int i = 0; i < runningServices.size(); ++i) {
                if (runningServices.get(i).process.contains("fridaserver") || runningServices.get(i).process.contains("frida-server")) {
                    return true;
                }
            }
        }

        return false;
    }
}

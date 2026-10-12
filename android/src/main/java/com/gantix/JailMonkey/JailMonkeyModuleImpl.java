package com.gantix.JailMonkey;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import androidx.annotation.Nullable;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Arguments;

import com.gantix.JailMonkey.Rooted.RootedCheck;

import static com.gantix.JailMonkey.AdbEnabled.AdbEnabled.AdbEnabled;
import static com.gantix.JailMonkey.ExternalStorage.ExternalStorageCheck.isOnExternalStorage;
import static com.gantix.JailMonkey.HookDetection.HookDetectionCheck.hookDetected;
import static com.gantix.JailMonkey.MockLocation.MockLocationCheck.isMockLocationOn;
import com.facebook.react.bridge.ReactApplicationContext;

/**
 * Logic class for JailMonkey module.
 * Contains all checks and returns values for the NativeJailMonkeySpec.
 */
public class JailMonkeyModuleImpl {

    public static final String NAME = "JailMonkey";

    
    private static ReactApplicationContext context;

    // The checks below hit the file system, the package manager and RootBeer's
    // native code, so they are run here rather than on the JS or UI thread.
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void init(ReactApplicationContext ctx) {
        context = ctx;
    }

    private static void resolveAsync(final Promise p, final Callable<Boolean> check) {
        executor.execute(() -> {
            try {
                p.resolve(check.call());
            } catch (Exception e) {
                p.reject("E_JAIL_MONKEY", e);
            }
        });
    }

    public static void isDevelopmentSettingsMode(Promise p) {
        boolean isDevelopmentSettingsMode;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDevelopmentSettingsMode = Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED,
                    0
            ) == 1;
        } else {
            isDevelopmentSettingsMode = Settings.Global.getInt(
                    context.getContentResolver(),
                    Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                    0
            ) == 1;
        }
        p.resolve(isDevelopmentSettingsMode);
    }

    public static void isDebuggedMode(Promise p) {
        resolveAsync(p, JailMonkeyModuleImpl::isDebuggedMode);
    }

    public static boolean isDebuggedMode() {
        if (Debug.isDebuggerConnected()) {
            return true;
        }
        return (context.getApplicationContext().getApplicationInfo().flags
                & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    public static void trustFall(Promise p) {
        resolveAsync(p, JailMonkeyModuleImpl::trustFall);
    }

    public static boolean trustFall() {
        return isJailBroken() || canMockLocation();
    }

     public static boolean isAdbEnabled() {
        if (context == null) return false;
        return AdbEnabled(context);
    }

    public static boolean isOnExternalStorage() {
        if (context == null) return false;
        return com.gantix.JailMonkey.ExternalStorage.ExternalStorageCheck.isOnExternalStorage(context);
    }

    public static String jailBrokenMessage() {
        return "null";
    }

    public static void canMockLocation(Promise p) {
        resolveAsync(p, JailMonkeyModuleImpl::canMockLocation);
    }

    public static boolean canMockLocation() {
        if (context == null) return false;
        return isMockLocationOn(context);
    }

    public static boolean hookDetected() {
        if (context == null) return false;
        return com.gantix.JailMonkey.HookDetection.HookDetectionCheck.hookDetected(context);
    }

    public static WritableMap rootedDetectionMethods() {
        RootedCheck rootedCheck = new RootedCheck(context);
        Map<String, Object> result = rootedCheck.getResultByDetectionMethod();

        WritableMap map = Arguments.createMap();

        for (Map.Entry<String, Object> entry : result.entrySet()) {
            map.putBoolean(entry.getKey(), (Boolean) entry.getValue());
        }

        return map;
    }

    public static void isJailBroken(Promise p) {
        resolveAsync(p, JailMonkeyModuleImpl::isJailBroken);
    }

    public static boolean isJailBroken() {
        if (context == null) return false;
        RootedCheck rootedCheck = new RootedCheck(context);
        return rootedCheck.isJailBroken();
    }
}

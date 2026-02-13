package com.gantix.JailMonkey;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import androidx.annotation.Nullable;
import java.util.Map;
import java.util.HashMap;
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

    public static void init(ReactApplicationContext ctx) {
        context = ctx;
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
        boolean isDebuggedMode;
        if (Debug.isDebuggerConnected()) {
            isDebuggedMode = true;
        } else {
            isDebuggedMode = (context.getApplicationContext().getApplicationInfo().flags
                    & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        p.resolve(isDebuggedMode);
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

    public static boolean isJailBroken() {
        if (context == null) return false;
        RootedCheck rootedCheck = new RootedCheck(context);
        return rootedCheck.isJailBroken();
    }
}

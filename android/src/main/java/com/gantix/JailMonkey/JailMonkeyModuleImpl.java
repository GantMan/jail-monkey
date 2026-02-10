package com.gantix.JailMonkey;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import com.facebook.react.bridge.Promise;
import java.util.Map;
import com.facebook.react.bridge.ReactMethod;
import java.util.HashMap;
import androidx.annotation.Nullable;

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
        return false;
    }

     public static boolean isAdbEnabled() {
        if (context == null) return false;
        return AdbEnabled(context);
    }

    public static boolean isOnExternalStorage() {
        if (context == null) return false;
        return isOnExternalStorage();
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

    public static Map<String,Object> rootedDetectionMethods() {
        RootedCheck rootedCheck = new RootedCheck(context);
        return rootedCheck.getResultByDetectionMethod();
    }

    public static boolean isJailBroken() {
        if (context == null) return false;
        RootedCheck rootedCheck = new RootedCheck(context);
        return rootedCheck.isJailBroken();
    }
}

package com.gantix.JailMonkey;

import android.content.pm.ApplicationInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.gantix.JailMonkey.Rooted.RootedCheck;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import static com.gantix.JailMonkey.AdbEnabled.AdbEnabled.AdbEnabled;
import static com.gantix.JailMonkey.ExternalStorage.ExternalStorageCheck.isOnExternalStorage;
import static com.gantix.JailMonkey.HookDetection.HookDetectionCheck.hookDetected;
import static com.gantix.JailMonkey.MockLocation.MockLocationCheck.isMockLocationOn;

public class JailMonkeyModule extends ReactContextBaseJavaModule {

    ReactApplicationContext reactContext;


    public JailMonkeyModule(ReactApplicationContext reactContext, boolean loadConstantsAsynchronously) {
        super(reactContext);

        this.reactContext = reactContext;

    }

    @Override
    public String getName() {
        return "JailMonkey";
    }


    @ReactMethod
    public void isDevelopmentSettingsMode(Promise p) {
        boolean isDevelopmentSettingsMode;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDevelopmentSettingsMode = Settings.System.getInt(this.reactContext.getContentResolver(), Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, 0) != 1;
        } else {
            isDevelopmentSettingsMode = Settings.Global.getInt(this.reactContext.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0) == 1;
        }
        p.resolve(isDevelopmentSettingsMode);
    }


    @ReactMethod
    public void isDebuggedMode(Promise p) {
        boolean isDebuggedMode;
        if (Debug.isDebuggerConnected()) {
            isDebuggedMode = true;
        } else {
            isDebuggedMode = (this.reactContext.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
        p.resolve(isDebuggedMode);
    }


    @Override
    public @Nullable
    Map<String, Object> getConstants() {
        ReactContext context = getReactApplicationContext();
        final RootedCheck rootedCheck = new RootedCheck(context);

        final Map<String, Object> constants = new HashMap<>();

        constants.put("isJailBroken", rootedCheck.isJailBroken());
        constants.put("rootedDetectionMethods", rootedCheck.getResultByDetectionMethod());
        constants.put("hookDetected", hookDetected(context));
        constants.put("canMockLocation", isMockLocationOn(context));
        constants.put("isOnExternalStorage", isOnExternalStorage(context));
        constants.put("AdbEnabled", AdbEnabled(context));
        return constants;
    }
}

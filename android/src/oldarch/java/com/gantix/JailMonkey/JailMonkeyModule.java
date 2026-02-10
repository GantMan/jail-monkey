package com.gantix.JailMonkey;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class JailMonkeyModule extends ReactContextBaseJavaModule {

    private final JailMonkeyImpl impl;

    public JailMonkeyModule(ReactApplicationContext reactContext) {
        super(reactContext);
        impl = new JailMonkeyImpl(reactContext); // context impl içinde set ediliyor
    }

    @Override
    public String getName() {
        return "JailMonkey";
    }

    // ===== ASYNC METHODS =====
    @ReactMethod
    public void isDevelopmentSettingsMode(Promise p) {
        impl.isDevelopmentSettingsMode(p);
    }

    @ReactMethod
    public void isDebuggedMode(Promise p) {
        impl.isDebuggedMode(p);
    }

    // ===== SYNC / CONSTANTS =====
    @Override
    public @Nullable Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();

        constants.put("isJailBroken", impl.isJailBroken());
        constants.put("hookDetected", impl.hookDetected());
        constants.put("canMockLocation", impl.canMockLocation());
        constants.put("isOnExternalStorage", impl.isOnExternalStorage());
        constants.put("AdbEnabled", impl.isAdbEnabled());

        return constants;
    }
}

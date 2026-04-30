package com.gantix.JailMonkey;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class JailMonkeyModule extends ReactContextBaseJavaModule {

    public JailMonkeyModule(ReactApplicationContext reactContext) {
        super(reactContext);
        JailMonkeyModuleImpl.init(reactContext);
    }

    @Override
    public String getName() {
        return "JailMonkey";
    }

    @ReactMethod
    public void isDevelopmentSettingsMode(Promise p) {
        JailMonkeyModuleImpl.isDevelopmentSettingsMode(p);
    }

    @ReactMethod
    public void isDebuggedMode(Promise p) {
        JailMonkeyModuleImpl.isDebuggedMode(p);
    }

    @Override
    public @Nullable Map<String, Object> getConstants() {
        Map<String, Object> constants = new HashMap<>();

        constants.put("isJailBroken", JailMonkeyModuleImpl.isJailBroken());
        constants.put("hookDetected", JailMonkeyModuleImpl.hookDetected());
        constants.put("canMockLocation", JailMonkeyModuleImpl.canMockLocation());
        constants.put("isOnExternalStorage", JailMonkeyModuleImpl.isOnExternalStorage());
        constants.put("AdbEnabled", JailMonkeyModuleImpl.isAdbEnabled());
        constants.put("rootedDetectionMethods", JailMonkeyModuleImpl.rootedDetectionMethods());

        return constants;
    }
}

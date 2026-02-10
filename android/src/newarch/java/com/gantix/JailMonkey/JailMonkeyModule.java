package com.gantix.JailMonkey;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import java.util.Map;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import androidx.annotation.Nullable;

/**
 * New Architecture TurboModule implementation.
 */
public class JailMonkeyModule extends NativeJailMonkeySpec {


    JailMonkeyModule(ReactApplicationContext context) {
        super(context);
        JailMonkeyModuleImpl.init(context);
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
    public boolean AdbEnabled() {
        return JailMonkeyModuleImpl.isAdbEnabled();
    }

    public boolean isOnExternalStorage() {
        return JailMonkeyModuleImpl.isOnExternalStorage();
    }

    public boolean canMockLocation() {
        return JailMonkeyModuleImpl.canMockLocation();
    }


    public boolean hookDetected() {
        return JailMonkeyModuleImpl.hookDetected();
    }

    public boolean trustFall() {
        return JailMonkeyModuleImpl.trustFall();
    }


    public Map<String,Object> rootedDetectionMethods() {
        return JailMonkeyModuleImpl.rootedDetectionMethods();
    }

    public String jailBrokenMessage() {
        return JailMonkeyModuleImpl.jailBrokenMessage();
    }

    public boolean isJailBroken() {
        return JailMonkeyModuleImpl.isJailBroken();
    }


}

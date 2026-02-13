package com.gantix.JailMonkey;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import java.util.Map;
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

    @Override
    public void isDevelopmentSettingsMode(Promise p) {
        JailMonkeyModuleImpl.isDevelopmentSettingsMode(p);
    }

    @Override
    public void isDebuggedMode(Promise p) {
        JailMonkeyModuleImpl.isDebuggedMode(p);
    }

    @Override
    public boolean AdbEnabled() {
        return JailMonkeyModuleImpl.isAdbEnabled();
    }

    @Override
    public boolean isOnExternalStorage() {
        return JailMonkeyModuleImpl.isOnExternalStorage();
    }

    @Override
    public boolean canMockLocation() {
        return JailMonkeyModuleImpl.canMockLocation();
    }

    @Override
    public boolean hookDetected() {
        return JailMonkeyModuleImpl.hookDetected();
    }

    @Override
    public boolean trustFall() {
        return JailMonkeyModuleImpl.trustFall();
    }

    @Override
    public Map<String,Object> rootedDetectionMethods() {
        return JailMonkeyModuleImpl.rootedDetectionMethods();
    }

    @Override
    public String jailBrokenMessage() {
        return JailMonkeyModuleImpl.jailBrokenMessage();
    }

    @Override
    public boolean isJailBroken() {
        return JailMonkeyModuleImpl.isJailBroken();
    }
}

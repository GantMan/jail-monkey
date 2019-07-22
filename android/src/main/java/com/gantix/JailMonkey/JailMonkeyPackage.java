package com.gantix.JailMonkey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

public class JailMonkeyPackage implements ReactPackage {

    private boolean mLoadConstantsAsynchronously;

    public JailMonkeyPackage() {
        this(false);
    }

    public JailMonkeyPackage(boolean loadConstantsAsynchronously) {
        mLoadConstantsAsynchronously = loadConstantsAsynchronously;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        modules.add(new JailMonkeyModule(reactContext, mLoadConstantsAsynchronously));
        return modules;
    }

    // Deprecated RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(
            ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }

}

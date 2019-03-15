package com.gantix.JailMonkey;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import java.io.BufferedReader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStreamReader;

import static com.gantix.JailMonkey.ExternalStorage.ExternalStorageCheck.isOnExternalStorage;
import static com.gantix.JailMonkey.MockLocation.MockLocationCheck.isMockLocationOn;
import static com.gantix.JailMonkey.Rooted.RootedCheck.isJailBroken;
import static com.gantix.JailMonkey.Debugged.DebuggedCheck.isDebugged;
import static com.gantix.JailMonkey.Rooted.HookDetection.hookDetected;

public class JailMonkeyModule extends ReactContextBaseJavaModule {

  public JailMonkeyModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  public String getName() {
      return "JailMonkey";
  }

  @Override
  public Map<String, Object> getConstants() {
    ReactContext context = getReactApplicationContext();
    final Map<String, Object> constants = new HashMap<>();
    constants.put("isJailBroken", isJailBroken(context));
    constants.put("hookDetected", hookDetected(context));
    constants.put("canMockLocation", isMockLocationOn(context));
    constants.put("isOnExternalStorage", isOnExternalStorage(context));
    constants.put("isDebugged", isDebugged(context));
    return constants;
  }
}

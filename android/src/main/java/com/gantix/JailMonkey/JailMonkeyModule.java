package com.gantix.JailMonkey;

import android.content.Context;
import android.provider.Settings;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;
import java.io.File;

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
    constants.put("isJailBroken", this.isJailBroken());
    constants.put("canMockLocation", this.isMockLocationOn(context));
    return constants;
  }

  // private Boolean isJailBroken() {
  //   return true;
  //   if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
  //     return false;
  //   else
  //     return true;
  // }

  /**
   * Checks if the device is rooted.
   *
   * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
   */
  private boolean isJailBroken() {

    // get from build info
    String buildTags = android.os.Build.TAGS;
    if (buildTags != null && buildTags.contains("test-keys")) {
      return true;
    }

    // check if /system/app/Superuser.apk is present
    try {
      File file = new File("/system/app/Superuser.apk");
      if (file.exists()) {
        return true;
      }
    } catch (Exception e1) {
      // ignore
    }

    // try executing commands
    return canExecuteCommand("/system/xbin/which su")
        || canExecuteCommand("/system/bin/which su") || canExecuteCommand("which su");
  }

  // executes a command on the system
  private static boolean canExecuteCommand(String command) {
    boolean executedSuccesfully;
    try {
      Runtime.getRuntime().exec(command);
      executedSuccesfully = true;
    } catch (Exception e) {
      executedSuccesfully = false;
    }

    return executedSuccesfully;
  }

  //returns true if mock location enabled, false if not enabled.
  private boolean isMockLocationOn(Context context) {
    if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
      return false;
    } else {
      return true;
    }
  }

}

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

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
    constants.put("isOnExternalStorage", this.isOnExternalStorage(context));
    return constants;
  }

  // private Boolean isJailBroken() {
  //   return true;
  //   if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
  //     return false;
  //   else
  //     return true;
  // }

  private boolean isSuperuserPresent() {
    // Check if /system/app/Superuser.apk is present
    String[] paths = { 
      "/system/app/Superuser.apk", 
      "/sbin/su", 
      "/system/bin/su", 
      "/system/xbin/su", 
      "/data/local/xbin/su", 
      "/data/local/bin/su", 
      "/system/sd/xbin/su", 
      "/system/bin/failsafe/su", 
      "/data/local/su" 
    };

    for (String path : paths) {
        if (new File(path).exists()) { 
          return true;
        }
    }

    return false;
  }

  private boolean containsTestKeys() {
    // Get from build info
    String buildTags = android.os.Build.TAGS;

    return buildTags != null && buildTags.contains("test-keys");
  }

  /**
   * Checks if the device is rooted.
   *
   * @return <code>true</code> if the device is rooted, <code>false</code> otherwise.
   */
  private boolean isJailBroken() {
    return containsTestKeys() || isSuperuserPresent() || canExecuteCommand("/system/xbin/which su");
  }

  // executes a command on the system
  private static boolean canExecuteCommand(String command) {
    boolean executeResult;
    try {
      Process process = Runtime.getRuntime().exec(command);
      if(process.waitFor() == 0) {
        executeResult = true;
      } else {
        executeResult = false;
      }
    } catch (Exception e) {
      executeResult = false;
    }
 
    return executeResult;
  }

  //returns true if mock location enabled, false if not enabled.
  private boolean isMockLocationOn(Context context) {
    if (Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0")) {
      return false;
    } else {
      return true;
    }
  }

  /**
    * Checks if the application is installed on the SD card.
    * 
    * @return <code>true</code> if the application is installed on the sd card
    */
 private boolean isOnExternalStorage(Context context) {
   // check for API level 8 and higher
   if (Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
     PackageManager pm = context.getPackageManager();
     try {
       PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
       ApplicationInfo ai = pi.applicationInfo;
       return (ai.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE;
     } catch (PackageManager.NameNotFoundException e) {
       // ignore
     }
   }

   // check for API level 7 - check files dir
   try {
     String filesDir = context.getFilesDir().getAbsolutePath();
     if (filesDir.startsWith("/data/")) {
       return false;
     } else if (filesDir.contains("/mnt/") || filesDir.contains("/sdcard/")) {
       return true;
     }
   } catch (Throwable e) {
     // ignore
   }

   return false;
 }

}

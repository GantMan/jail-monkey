package com.gantix.JailMonkey.MockLocation;

import android.content.Context;
import android.provider.Settings;
import android.os.Build;
import android.util.Log;
import com.facebook.react.bridge.ReactContext;
import android.content.pm.PackageManager;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PackageInfo;
import android.location.LocationManager;

import java.lang.IllegalArgumentException;

public class MockLocationCheck {
    public static boolean isMockLocationOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return "0".equals(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION));
        } else {
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages =
                    pm.getInstalledApplications(PackageManager.GET_META_DATA);

            if (packages != null) {
                for (ApplicationInfo applicationInfo : packages) {
                    try {
                        PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                                PackageManager.GET_PERMISSIONS);

                        // Get Permissions
                        String[] requestedPermissions = packageInfo.requestedPermissions;

                        if (requestedPermissions != null) {
                            for (int i = 0; i < requestedPermissions.length; i++) {
                                if (requestedPermissions[i]
                                        .equals("android.permission.ACCESS_MOCK_LOCATION")
                                        && !applicationInfo.packageName.equals(context.getPackageName())) {
                                    return true;
                                }
                            }
                        }
                    } catch (NameNotFoundException e) {
                        Log.e("Mock location check error", e.getMessage());
                    }
                }
            }

            return false;
        }
    }
}

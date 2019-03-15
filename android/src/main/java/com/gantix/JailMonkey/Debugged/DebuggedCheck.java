package com.gantix.JailMonkey.Debugged;

import android.content.Context;
import android.os.Debug;
import android.content.pm.ApplicationInfo;

public class DebuggedCheck {
    /**
     * Checks if the device is in debug mode.
     *
     * @return <code>true</code> if the device is debug mode, <code>false</code> otherwise.
     */
    public static boolean isDebugged(Context context) {
        if (Debug.isDebuggerConnected()) {
            return true;
        }

        return (context.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }
}

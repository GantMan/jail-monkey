import { NativeModules, Platform } from "react-native";

const { JailMonkey } = NativeModules;

export default {
  isJailBroken: () => JailMonkey.isJailBroken || false,
  hookDetected: () => JailMonkey.hookDetected || false,
  canMockLocation: () => JailMonkey.canMockLocation || false,
  trustFall: () => JailMonkey.isJailBroken || JailMonkey.canMockLocation || false,
  isOnExternalStorage: () => JailMonkey.isOnExternalStorage || false,
  isDebuggedMode: () => JailMonkey.isDebuggedMode(),
  isDevelopmentSettingsMode: () => {
    // API only available on Android, return false for all other platforms.
    if (Platform.OS !== 'android') return Promise.resolve(false);
    return JailMonkey.isDevelopmentSettingsMode()
  },
  AdbEnabled: () => JailMonkey.AdbEnabled || false,
};

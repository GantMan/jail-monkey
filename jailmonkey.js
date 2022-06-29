import { NativeModules, Platform } from "react-native";

const { JailMonkey } = NativeModules;

if (JailMonkey == null) console.warn("JailMonkey is not available, check your native dependencies have linked correctly and ensure your app has been rebuilt");

export default {
  jailBrokenMessage: () => JailMonkey.jailBrokenMessage || "",
  isJailBroken: () => JailMonkey.isJailBroken || false,
  androidRootedDetectionMethods: JailMonkey.rootedDetectionMethods,
  hookDetected: () => JailMonkey.hookDetected || false,
  canMockLocation: () => JailMonkey.canMockLocation || false,
  trustFall: () =>
    JailMonkey.isJailBroken || JailMonkey.canMockLocation || false,
  isOnExternalStorage: () => JailMonkey.isOnExternalStorage || false,
  isDebuggedMode: () => JailMonkey.isDebuggedMode(),
  isDevelopmentSettingsMode: () => {
    // API only available on Android, return false for all other platforms.
    if (Platform.OS !== "android") return Promise.resolve(false);
    return JailMonkey.isDevelopmentSettingsMode();
  },
  AdbEnabled: () => JailMonkey.AdbEnabled || false,
};

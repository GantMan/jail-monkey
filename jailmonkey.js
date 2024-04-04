import { NativeModules, Platform } from "react-native";

const message = "JailMonkey native module is not available, check your native dependencies have linked correctly and ensure your app has been rebuilt";

if (NativeModules.JailMonkey == null) console.warn(message);

function getJailMonkey() {
  const { JailMonkey } = NativeModules;
  if (JailMonkey == null) throw new Error(message)
  return JailMonkey;
}

export default {
  jailBrokenMessage: () => getJailMonkey().jailBrokenMessage || "",
  isJailBroken: () => getJailMonkey().isJailBroken || false,
  androidRootedDetectionMethods: NativeModules.JailMonkey?.rootedDetectionMethods ?? {},
  hookDetected: () => getJailMonkey().hookDetected || false,
  canMockLocation: () => getJailMonkey().canMockLocation || false,
  trustFall: () =>
    getJailMonkey().isJailBroken || getJailMonkey().canMockLocation || false,
  isOnExternalStorage: () => getJailMonkey().isOnExternalStorage || false,
  isDebuggedMode: () => getJailMonkey().isDebuggedMode(),
  isDevelopmentSettingsMode: () => {
    // API only available on Android, return false for all other platforms.
    if (Platform.OS !== "android") return Promise.resolve(false);
    return getJailMonkey().isDevelopmentSettingsMode();
  },
  AdbEnabled: () => getJailMonkey().AdbEnabled || false,
};

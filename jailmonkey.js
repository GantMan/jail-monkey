import { NativeModules } from "react-native";

const { JailMonkey } = NativeModules;

export default {
  isJailBroken: () => JailMonkey.isJailBroken,
  hookDetected: () => JailMonkey.hookDetected,
  canMockLocation: () => JailMonkey.canMockLocation,
  trustFall: () => JailMonkey.isJailBroken || JailMonkey.canMockLocation,
  isOnExternalStorage: () => JailMonkey.isOnExternalStorage,
  isDebuggedMode: function() {
    return JailMonkey.isDebuggedMode();
  },
  isDevelopmentSettingsMode: function() {
    return JailMonkey.isDevelopmentSettingsMode();
  },
  AdbEnabled: () => JailMonkey.AdbEnabled
};

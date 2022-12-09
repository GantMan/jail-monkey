// should be imported this way:
// import JailMonkey from 'jail-monkey';

declare const _default: {
  jailBrokenMessage : () => string,
  isJailBroken: () => boolean;
  androidRootedDetectionMethods?: {
    rootBeer: {
      detectRootManagementApps: boolean;
      detectPotentiallyDangerousApps: boolean;
      checkForSuBinary: boolean;
      checkForDangerousProps: boolean;
      checkForRWPaths: boolean;
      detectTestKeys: boolean;
      checkSuExists: boolean;
      checkForRootNative: boolean;
      checkForMagiskBinary: boolean;
    },
    jailMonkey: boolean;
  };
  hookDetected: () => boolean;
  isDebuggedMode: () => Promise<boolean>;
  canMockLocation: () => boolean;
  trustFall: () => boolean;
  isOnExternalStorage: () => boolean;
  AdbEnabled: () => boolean;
  isDevelopmentSettingsMode: () => Promise<boolean>;
};

export default _default;

// should be imported this way:
// import JailMonkey from 'jail-monkey';

declare const _default: {
  jailBrokenMessage : () => string,
  isJailBroken: () => boolean;
  hookDetected: () => boolean;
  isDebuggedMode: () => Promise<boolean>;
  canMockLocation: () => boolean;
  trustFall: () => boolean;
  isOnExternalStorage: () => boolean;
  AdbEnabled: () => boolean;
  isDevelopmentSettingsMode: () => Promise<boolean>;
};

export default _default;

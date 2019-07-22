// should be imported this way:
// import JailMonkey from 'jail-monkey';


declare const _default: {
  isJailBroken: () => Promise<boolean>;
  isDebuggedMode: () => Promise<boolean>;
  canMockLocation: () => Promise<boolean>;
  trustFall: () => Promise<boolean>;
  isOnExternalStorage: () => Promise<boolean>;
  AdbEnabled: () => Promise<boolean>;
  isDevelopmentSettingsMode: () => Promise<boolean>;
};

export default _default;

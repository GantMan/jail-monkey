import type {TurboModule} from 'react-native';
import {TurboModuleRegistry} from 'react-native';

export interface Spec extends TurboModule {
  jailBrokenMessage(): string;
  isJailBroken(): boolean;
  hookDetected(): boolean;
  canMockLocation(): boolean;
  trustFall(): boolean;
  isOnExternalStorage(): boolean;
  AdbEnabled(): boolean;
  isDebuggedMode(): Promise<boolean>;
  isDevelopmentSettingsMode(): Promise<boolean>;
  rootedDetectionMethods?: () => Record<string, boolean>;
}

export default TurboModuleRegistry.getEnforcing<Spec>(
  'JailMonkey'
);

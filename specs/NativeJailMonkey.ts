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
  isJailBrokenAsync(): Promise<boolean>;
  canMockLocationAsync(): Promise<boolean>;
  trustFallAsync(): Promise<boolean>;
  isDebuggedMode(): Promise<boolean>;
  isDevelopmentSettingsMode(): Promise<boolean>;
  rootedDetectionMethods?: () => { [key: string]: boolean };
}

export default TurboModuleRegistry.getEnforcing<Spec>(
  'JailMonkey'
);

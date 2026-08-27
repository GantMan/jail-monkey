// @ts-expect-error — react-native is a peer dependency, not installed at the package root
import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'jail-monkey' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// Prefer TurboModule when available.
// Expo SDK / RN setup can make `global.__turboModuleProxy` unreliable, so
// we attempt to load the TurboModule and fall back to legacy NativeModules.
let turboModule: any = null;
try {
  // This will throw if the TurboModule isn't registered/available.
  // eslint-disable-next-line @typescript-eslint/no-var-requires, @typescript-eslint/ban-ts-comment
  // @ts-expect-error — dynamic require so missing spec doesn't break legacy builds
  turboModule = require('./specs/NativeJailMonkey.ts').default;
} catch {
  turboModule = null;
}

const legacy = NativeModules.JailMonkey;
const source: any = turboModule ?? legacy;

const wrapBool = (v: any) =>
  typeof v === 'function' ? v : () => Boolean(v);

// If neither the TurboModule nor the legacy NativeModule resolved, the package
// is not linked. Return a Proxy so any method call throws an explicit linking
// error instead of silently returning `false`/`''` (which would make a missing
// install look like a clean, non-jailbroken device).
const exported: any =
  source == null
    ? new Proxy(
        {},
        {
          get() {
            return () => {
              throw new Error(LINKING_ERROR);
            };
          },
        },
      )
    : {
        // Both TurboModule and legacy NativeModules may expose values from
        // `constantsToExport` as plain booleans instead of functions. Wrap
        // them so callers can consistently use `JailMonkey.isJailBroken()`.
        jailBrokenMessage:
          typeof source.jailBrokenMessage === 'function'
            ? source.jailBrokenMessage
            : () => String(source.jailBrokenMessage ?? ''),
        isJailBroken: wrapBool(source.isJailBroken),
        canMockLocation: wrapBool(source.canMockLocation),
        trustFall:
          source.trustFall != null
            ? wrapBool(source.trustFall)
            : () => exported.isJailBroken() || exported.canMockLocation(),
        hookDetected: wrapBool(source.hookDetected ?? false),
        isOnExternalStorage: wrapBool(source.isOnExternalStorage ?? false),
        AdbEnabled: wrapBool(source.AdbEnabled ?? false),
        isDebuggedMode:
          typeof source.isDebuggedMode === 'function'
            ? source.isDebuggedMode
            : async () => false,
        isDevelopmentSettingsMode:
          typeof source.isDevelopmentSettingsMode === 'function'
            ? source.isDevelopmentSettingsMode
            : async () => false,
        // Old-arch Android exposes this as a plain object via getConstants();
        // new-arch / TurboModule exposes it as a function. Normalize to a
        // callable so consumers get the same shape on every platform.
        rootedDetectionMethods:
          typeof source.rootedDetectionMethods === 'function'
            ? source.rootedDetectionMethods
            : source.rootedDetectionMethods != null
              ? () => source.rootedDetectionMethods
              : undefined,
      };
export default exported;

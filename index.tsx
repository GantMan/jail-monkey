import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'jail-monkey' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

// @ts-expect-error
const isTurboModuleEnabled = global.__turboModuleProxy != null;

export default isTurboModuleEnabled
  ? require('./specs/NativeJailMonkey.ts').default
  : NativeModules.JailMonkey;

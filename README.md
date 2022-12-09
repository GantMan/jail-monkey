# WARNING:  I don't have the devices to test anymore, so testing is done by those submitting PRs bona fide.

![Jail Monkey](./_art/JailMonkey.jpg)

[![Version](https://img.shields.io/npm/v/jail-monkey.svg)](https://www.npmjs.com/package/jail-monkey) [![Downloads](https://img.shields.io/npm/dm/jail-monkey.svg)](https://npmcharts.com/compare/jail-monkey?minimal=true)

## Can you ever really trust a phone?

# Why?
Are users claiming they are crossing the globe in seconds and collecting all the Pokeballs?  Some apps need to protect themselves in order to protect data integrity.  JailMonkey allows you to:
* Identify if a phone has been jail-broken or rooted for iOS/Android.
* Detect mocked locations for phones set in "developer mode".
* **(ANDROID ONLY)** Detect if the application is running on external storage such as an SD card.

# Use
```javascript
import JailMonkey from 'jail-monkey'

if (JailMonkey.isJailBroken()) {
  // Alternative behaviour for jail-broken/rooted devices.
}
```

![Circle of Trust](./_art/trust.jpg)

# API

Method | Returns | Description
---|---|---
`isJailBroken` | `boolean` | is this device jail-broken/rooted.
`canMockLocation` | `boolean` | Can this device fake its GPS location.
`trustFall` | `boolean` | Checks if the device violates either `isJailBroken` or `canMockLocation`.
`isDebuggedMode` | `Promise<boolean>` | Is the application is running in debug mode. Note that this method returns a Promise.

## iOS Only APIs

Method | Returns | Description
---|---|---
`jailBrokenMessage` | `string` | Returns the reason for jailbroken detection. Will return an empty string on Android.

## Android Only APIs

Method | Returns | Description
---|---|---
`hookDetected` | `boolean` | Detects if there is any suspicious installed applications.
`isOnExternalStorage` | `boolean` | Is the application running on external storage (ie. SD Card)
`AdbEnabled` | `boolean` | Is Android Debug Bridge enabled.
`isDevelopmentSettingsMode` | `Promise<boolean>` | Whether user has enabled development settings on their device. Note that this method returns a Promise.
`androidRootedDetectionMethods` | `RootedDetectionMethods` | Returns an object with the results of all the Android rooted detection methods for more granular detection, this can be helpful if some devices are giving false positives.

```
type RootedDetectionMethods = {
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
}
```

On iOS all of the Android only methods will return `false` or `Promise<false>` where appropriate.

### :exclamation: Since emulators are usually rooted, you might want to bypass these checks during development.  Unless you're keen on constant false alarms :alarm_clock:

# Install

```bash
npm i jail-monkey --save
react-native link # Not required as of React Native 0.60.0
```
for iOS:
```bash
cd ios && pod install
```

If you use `rnpm`, you may have trouble as `rnpm` does not link Android properly after 0.29.0!

Note: On Android you should include `location.isFromMockProvider()` from your location provider to compliment `JailMonkey.canMockLocation()`.  Most react-native location libraries already have this check built in

# Additional Info
This has been made public to help keep it up to date.  As detection measures get better or out-dated, please send updates to this project so it can be the best method of detection.

Special thanks to this fantastic blog article:  http://blog.geomoby.com/2015/01/25/how-to-avoid-getting-your-location-based-app-spoofed/

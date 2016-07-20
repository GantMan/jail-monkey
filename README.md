![Jail Monkey](./_art/JailMonkey.jpg)
## Can you trust this phone?

# Why?
Are users claiming they are crossing the globe in seconds and collecting all the Pokeballs?  Some apps need to protect themselves, in order to protect data integrity.  JailMonkey allows you to identify if a phone has been jail-broken or rooted for iOS/Android.  It also can help you detect mocked locations.

# Use
```javascript
import JailMonkey from 'jail-monkey'

// true/false of device JailBroken on iOS/Android
JailMonkey.isJailBroken()

// More prevalent on Android, not rooted but mocking location data
JailMonkey.canMockLocation()

// Check if device violates any of the above
JailMonkey.trustFall()
```
![Circle of Trust](./_art/trust.jpg)

### :exclamation: Since Simulators/Emulators are usually rooted, you might want to bypass these checks during development.  Check `__DEV__` before running, to avoid constant false alarms

# Install

### Using RNPM
```
rnpm install jail-monkey
```

### Using react-native CLI (RN Project 0.28+)
```
npm i jail-monkey --save
react-native link
```

# Additional Info
This has been made public to help keep it up to date.  As detection measures get better or out-dated; please send updates to this project so it can be the best method of detection.

All methods for the initialization of this project were gathered from the fantastic blog article listed here:  http://blog.geomoby.com/2015/01/25/how-to-avoid-getting-your-location-based-app-spoofed/

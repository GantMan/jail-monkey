# WARNING:  I don't have the devices to test anymore, so testing is done by those submitting PRs bona fide.

![Jail Monkey](./_art/JailMonkey.jpg)
## Can you ever really trust a phone?

# Why?
Are users claiming they are crossing the globe in seconds and collecting all the Pokeballs?  Some apps need to protect themselves in order to protect data integrity.  JailMonkey allows you to: 
* Identify if a phone has been jail-broken or rooted for iOS/Android.  
* Detect mocked locations for phones set in "developer mode".
* **(ANDROID ONLY)** Detect if the application is running on external storage such as an SD card.

# Use
```javascript
import JailMonkey from 'jail-monkey'

// is this device JailBroken on iOS/Android?
JailMonkey.isJailBroken()

// Can this device mock location - no need to root!
JailMonkey.canMockLocation()

// Check if device violates any of the above
JailMonkey.trustFall()

// (ANDROID ONLY) Check if application is running on external storage
JailMonkey.isOnExternalStorage()

// (ANDROID ONLY) Check if the phone has some malicious apps installed
JailMonkey.hookDetected()

// Check if the application is running in debug mode
JailMonkey.isDebugged()
```
![Circle of Trust](./_art/trust.jpg)

### :exclamation: Since emulators are usually rooted, you might want to bypass these checks during development.  Unless you're keen on constant false alarms :alarm_clock:

# Install

```
npm i jail-monkey --save
react-native link
```

If you use `rnpm`, you may have trouble as `rnpm` does not link Android properly after 0.29.0!

Note: On Android you should include `location.isFromMockProvider()` from your location provider to compliment `JailMonkey.canMockLocation()`.  Most react-native location libraries already have this check built in

# Additional Info
This has been made public to help keep it up to date.  As detection measures get better or out-dated, please send updates to this project so it can be the best method of detection.

Special thanks to this fantastic blog article:  http://blog.geomoby.com/2015/01/25/how-to-avoid-getting-your-location-based-app-spoofed/

![Jail Monkey](./_art/JailMonkey.jpg)
## Detect Devices with Mocked Locations in React-Native

# Why?
Are users claiming they are crossing the globe in seconds and collecting all the Pokeballs?  Some apps need to protect themselves, in order to protect data integrity.

# Use
```javascript
import JailMonkey from 'jail-monkey'

// returns boolean if device is JailBroken on iOS
JailMonkey.isJailBroken

```

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

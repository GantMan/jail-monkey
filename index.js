const react = require('react')
const rn = require('react-native')
let JailMonkey = null

// Detect location of native modules to support older RN
if (react.NativeModules && react.NativeModules.JailMonkey) {
  JailMonkey = react.NativeModules.JailMonkey
} else {
  JailMonkey = rn.NativeModules.JailMonkey
}

export default JailMonkey

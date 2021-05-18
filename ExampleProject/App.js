/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

import * as React from 'react';
import {SafeAreaView, ScrollView, View, StyleSheet, Text} from 'react-native';
import JailMonkey from 'jail-monkey';

/*
const JailMonkey: {
    isJailBroken: () => boolean;
    hookDetected: () => boolean;
    isDebuggedMode: () => Promise<boolean>;
    canMockLocation: () => boolean;
    trustFall: () => boolean;
    isOnExternalStorage: () => boolean;
    AdbEnabled: () => boolean;
    isDevelopmentSettingsMode: () => Promise<boolean>;
}
*/

export default function App() {
  const [isDevelopmentSettingsMode, setIsDevelopmentSettingsMode] =
    React.useState();
  const [isDebuggedMode, setIsDebuggedMode] = React.useState();

  React.useEffect(() => {
    JailMonkey.isDevelopmentSettingsMode()
      .then(value => {
        setIsDevelopmentSettingsMode(value);
      })
      .catch(console.warn);
  }, []);

  React.useEffect(() => {
    JailMonkey.isDebuggedMode()
      .then(value => {
        setIsDebuggedMode(value);
      })
      .catch(console.warn);
  }, []);

  return (
    <SafeAreaView style={styles.root}>
      <View style={styles.content} accessible={false} accessibilityLabel="">
        <Row label="isJailBroken" value={JailMonkey.isJailBroken()} />
        <Row label="canMockLocation" value={JailMonkey.canMockLocation()} />
        <Row label="trustFall" value={JailMonkey.trustFall()} />
        <Row label="hookDetected" value={JailMonkey.hookDetected()} />
        <Row
          label="isOnExternalStorage"
          value={JailMonkey.isOnExternalStorage()}
        />
        <Row label="AdbEnabled" value={JailMonkey.AdbEnabled()} />
        <Row
          label="isDevelopmentSettingsMode"
          value={isDevelopmentSettingsMode}
        />
        <Row label="isDebuggedMode" value={isDebuggedMode} />
      </View>
    </SafeAreaView>
  );
}

function Row({label, value}) {
  return (
    <View
      style={styles.row}
      accessibilityLabel={`${label}: ${value?.toString() ?? 'unknown'}`}>
      <Text style={styles.label}>{label}:</Text>
      <Text style={styles.value}>{value?.toString() ?? 'unknown'}</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  root: {
    flex: 1,
  },
  content: {
    padding: 20,
  },
  row: {
    flexDirection: 'row',
    marginBottom: 5,
  },
  label: {
    fontSize: 16,
    color: 'black',
    fontWeight: '700',
    marginRight: 10,
  },
  value: {
    fontSize: 16,
    color: 'black',
  },
});

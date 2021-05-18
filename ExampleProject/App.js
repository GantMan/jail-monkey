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
        <Text style={styles.title}>Android & iOS</Text>
        <Row label="isJailBroken" value={JailMonkey.isJailBroken()} />
        <Row label="canMockLocation" value={JailMonkey.canMockLocation()} />
        <Row label="trustFall" value={JailMonkey.trustFall()} />
        <Row label="isDebuggedMode" value={isDebuggedMode} />

        <Text style={styles.title}>Android</Text>
        <Text style={styles.note}>
          These APIs will always return false on iOS.
        </Text>
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
    backgroundColor: '#fff',
  },
  content: {
    padding: 20,
  },
  title: {
    fontSize: 26,
    color: '#000',
    fontWeight: '700',
    marginTop: 20,
    marginBottom: 5,
  },
  note: {
    fontSize: 11,
    color: '#888',
    marginBottom: 10,
  },
  row: {
    flexDirection: 'row',
    marginBottom: 5,
  },
  label: {
    fontSize: 16,
    color: '#444',
    fontWeight: '700',
    marginRight: 5,
  },
  value: {
    fontSize: 16,
    color: '#444',
  },
});

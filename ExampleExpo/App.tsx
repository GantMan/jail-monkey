import { useEffect, useState } from 'react';
import { StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { StatusBar } from 'expo-status-bar';
import JailMonkey from 'jail-monkey';

export default function App() {
  const [isDevelopmentSettingsMode, setIsDevelopmentSettingsMode] = useState<
    boolean | undefined
  >();
  const [isDebuggedMode, setIsDebuggedMode] = useState<boolean | undefined>();
  const [isJailBroken, setIsJailBroken] = useState<boolean | undefined>();
  const [canMockLocation, setCanMockLocation] = useState<boolean | undefined>();
  const [trustFall, setTrustFall] = useState<boolean | undefined>();

  useEffect(() => {
    JailMonkey.isDevelopmentSettingsMode()
      .then(setIsDevelopmentSettingsMode)
      .catch(console.warn);
  }, []);

  useEffect(() => {
    JailMonkey.isDebuggedMode().then(setIsDebuggedMode).catch(console.warn);
  }, []);

  useEffect(() => {
    JailMonkey.isJailBrokenAsync().then(setIsJailBroken).catch(console.warn);
    JailMonkey.canMockLocationAsync()
      .then(setCanMockLocation)
      .catch(console.warn);
    JailMonkey.trustFallAsync().then(setTrustFall).catch(console.warn);
  }, []);

  return (
    <SafeAreaView style={styles.root}>
      <StatusBar style="auto" />
      <View style={styles.content}>
        <Text style={styles.title}>Expo SDK 55 (New Architecture)</Text>
        <Text style={styles.subtitle}>jail-monkey from ../ (local)</Text>

        <Text style={styles.section}>Android & iOS</Text>
        <Row label="isJailBroken" value={JailMonkey.isJailBroken()} />
        <Row label="canMockLocation" value={JailMonkey.canMockLocation()} />
        <Row label="trustFall" value={JailMonkey.trustFall()} />
        <Row label="isJailBrokenAsync" value={isJailBroken} />
        <Row label="canMockLocationAsync" value={canMockLocation} />
        <Row label="trustFallAsync" value={trustFall} />
        <Row label="isDebuggedMode" value={isDebuggedMode} />

        <Text style={styles.section}>Android</Text>
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

function Row({
  label,
  value,
}: {
  label: string;
  value: string | boolean | undefined;
}) {
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
    fontSize: 22,
    color: '#000',
    fontWeight: '700',
    marginBottom: 4,
  },
  subtitle: {
    fontSize: 13,
    color: '#666',
    marginBottom: 16,
  },
  section: {
    fontSize: 20,
    color: '#000',
    fontWeight: '700',
    marginTop: 16,
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

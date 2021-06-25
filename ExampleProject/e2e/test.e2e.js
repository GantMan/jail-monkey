/* eslint-disable no-undef */

describe('Example', () => {
  beforeAll(async () => {
    await device.launchApp();
    await device.reloadReactNative();
  });

  it('isJailBroken', async () => {
    await expect(element(by.label('isJailBroken: true'))).toBeVisible();
  });

  it('canMockLocation', async () => {
    await expect(element(by.label('canMockLocation: false'))).toBeVisible();
  });

  it('trustFall', async () => {
    await expect(element(by.label('trustFall: true'))).toBeVisible();
  });

  it('hookDetected', async () => {
    await expect(element(by.label('hookDetected: false'))).toBeVisible();
  });

  it('isOnExternalStorage', async () => {
    await expect(element(by.label('isOnExternalStorage: false'))).toBeVisible();
  });

  it('AdbEnabled', async () => {
    await expect(element(by.label('AdbEnabled: true'))).toBeVisible();
  });

  it('isDevelopmentSettingsMode', async () => {
    await expect(
      element(by.label('isDevelopmentSettingsMode: false')),
    ).toBeVisible();
  });

  it('isDebuggedMode', async () => {
    await expect(element(by.label('isDebuggedMode: true'))).toBeVisible();
  });
});

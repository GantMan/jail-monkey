package com.gantix.JailMonkey.MockLocation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MockLocationCheckTest {

    @Test
    public void legacySettingIsEnabledOnlyForOne() {
        assertTrue(MockLocationCheck.isLegacyMockLocationEnabled("1"));
        assertFalse(MockLocationCheck.isLegacyMockLocationEnabled("0"));
        assertFalse(MockLocationCheck.isLegacyMockLocationEnabled(null));
    }
}

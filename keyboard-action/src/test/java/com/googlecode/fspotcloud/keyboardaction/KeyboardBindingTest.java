package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KeyboardBindingTest {

    public static final String MODE_ONE = "MODE_ONE";
    public static final String MODE_TWO = "MODE_TWO";
    public static final String MODE_THREE = "MODE_THREE";
    public static final String[] MODES = {MODE_ONE, MODE_TWO, MODE_THREE};


    final KeyStroke KEY_C = new KeyStroke('C');
    final KeyStroke KEY_B = new KeyStroke('B');
    final KeyStroke KEY_3 = new KeyStroke('3');


    final KeyboardBinding C_BINDING = KeyboardBinding.bind(KEY_C).override(MODE_TWO, KEY_B).withDefaultModes(MODE_ONE);

    @Test
    public void testGetKeys() throws Exception {
        KeyStroke[] keys = C_BINDING.getKeys(MODE_TWO);
        assertNotNull(keys);
        assertEquals(KEY_B, keys[0]);
        keys = C_BINDING.getKeys(MODE_ONE);
        assertNotNull(keys);
        assertEquals(KEY_C, keys[0]);

    }

    @Test
    public void testName() throws Exception {
        final KeyboardBinding THREE_BINDING = KeyboardBinding.bind(KEY_3).withDefaultModes(MODES).override(MODE_THREE);
        KeyStroke[] keys = THREE_BINDING.getKeys(MODE_THREE);
        assertEquals(0, keys.length);

    }
}

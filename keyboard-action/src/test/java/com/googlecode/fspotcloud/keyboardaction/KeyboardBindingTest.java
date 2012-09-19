package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class KeyboardBindingTest {

    public static final String MODE_ONE = "MODE_ONE";
    public static final String MODE_TWO = "MODE_TWO";

    final KeyStroke KEY_C = new KeyStroke('C');
    final KeyStroke KEY_B = new KeyStroke('B');


    final KeyboardBinding C_BINDING = KeyboardBinding.bind(KEY_C).override(MODE_TWO, KEY_B).withModes(MODE_ONE);

    @Test
    public void testGetKeys() throws Exception {
        KeyStroke[] keys = C_BINDING.getKeys(MODE_TWO);
        assertNotNull(keys);
        assertEquals(KEY_B, keys[0]);
        keys = C_BINDING.getKeys(MODE_ONE);
        assertNotNull(keys);
        assertEquals(KEY_C, keys[0]);

    }
}

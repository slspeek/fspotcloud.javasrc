package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class KeyboardPreferencesTest {
    public static final String MODE_ONE = "MODE_ONE";
    public static final String MODE_TWO = "MODE_TWO";
    public static final String MODE = "A";
    public static final String[] MODES = {MODE_ONE, MODE_TWO, MODE};
    final KeyStroke KEY_C = new KeyStroke('C');
    final KeyStroke KEY_B = new KeyStroke('B');
    public static final String ACTION_ID = "ACTION_ID";
    public static final String ACTION_2 = "ACTION2";
    private KeyboardPreferences keyboardPreferences;
    public static final char KEY_CODE = 'A';
    public static final KeyStroke SHIFT_A = new KeyStroke(Modifiers.SHIFT, KEY_CODE);
    final KeyboardBinding C_BINDING = KeyboardBinding.bind(KEY_C).override(MODE_TWO, KEY_B).withModes(MODE_ONE);

    @Before
    public void setUp() throws Exception {
        keyboardPreferences = new KeyboardPreferences(MODES);
    }

    @Test
    public void testBind() throws Exception {
        KeyboardBinding binding = KeyboardBinding.bind(SHIFT_A).withModes(MODE);
        keyboardPreferences.bind(ACTION_ID, binding);
        keyboardPreferences.bind(ACTION_2, C_BINDING);

        assertEquals(ACTION_ID, keyboardPreferences.get(MODE, SHIFT_A));
        assertEquals(ACTION_ID, keyboardPreferences.get(MODE, new KeyStroke(Modifiers.SHIFT, KEY_CODE)));
        assertEquals(ACTION_2, keyboardPreferences.get(MODE_ONE, KEY_C));
        assertEquals(ACTION_2, keyboardPreferences.get(MODE_TWO, KEY_B));

    }
}

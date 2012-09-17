package com.googlecode.fspotcloud.keyboardaction;

import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class KeyboardBinding {

    private final KeyStroke[] defaultKeys;
    private final Map<String, KeyStroke[]> binding = newHashMap();

    public KeyboardBinding(KeyStroke[] defaultKeys) {
        this.defaultKeys = defaultKeys;
    }

    public static KeyboardBinding bind(KeyStroke... keyStrokes) {
        KeyboardBinding keyboardBinding = new KeyboardBinding(keyStrokes);
        return keyboardBinding;
    }

    public KeyboardBinding override(String mode, KeyStroke... keyStrokes) {
        binding.put(mode, keyStrokes);
        return this;
    }

    public KeyStroke[] getKeys(String mode) {
        KeyStroke[] result = defaultKeys;
        KeyStroke[] override = binding.get(mode);
        if (override != null) {
            result = override;
        }
        return result;
    }

}

package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;

import java.util.Iterator;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
public class KeyboardBinding {

    private final KeyStroke[] defaultKeys;
    private final Map<String, KeyStroke[]> binding = newHashMap();
    private String[] modes;

    KeyboardBinding(KeyStroke[] defaultKeys) {
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

    public KeyboardBinding withModes(String... modes) {
        this.modes = modes;
        return this;
    }

    public KeyStroke[] getKeys(String mode) {
        KeyStroke[] result = {};
        if (newArrayList(modes).contains(mode)) {
            result = defaultKeys;
        }
        KeyStroke[] override = binding.get(mode);
        if (override != null) {
            result = override;
        }
        return result;
    }

    public String[] getModes() {
        return modes;
    }
}

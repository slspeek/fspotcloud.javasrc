package com.googlecode.fspotcloud.keyboardaction;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class KeyboardPreferences {

    private final String[] modes;
    private final Map<ActionKey, String> keyStringMap = newHashMap();

    public KeyboardPreferences(String[] modes) {
        this.modes = modes;
    }


    String get(String mode, KeyStroke keyStroke) {
        return null;
    }

    public void bind(String id, KeyboardBinding binding) {
        for (String mode : modes) {
            KeyStroke[] keys = binding.getKeys(mode);
            for (KeyStroke keyStroke : keys) {
                keyStringMap.put(new ActionKey(mode, keyStroke), id);
            }
        }
    }
}

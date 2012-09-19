package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
class KeyboardPreferences {

    private final Logger logger = Logger.getLogger(KeyboardPreferences.class.getName());
    private final String[] modes;
    private final Map<ActionKey, String> keyStringMap = newHashMap();

    public KeyboardPreferences(String[] modes) {
        this.modes = modes;
    }

    String get(String mode, KeyStroke keyStroke) {

        String result = keyStringMap.get(new ActionKey(mode, keyStroke));

        return result;
    }

    public void bind(String id, KeyboardBinding binding) {
        for (String mode : modes) {
            KeyStroke[] keys = binding.getKeys(mode);
            for (KeyStroke keyStroke : keys) {
                ActionKey key = new ActionKey(mode, keyStroke);
                logger.log(Level.FINEST, "putting keystroke: " +key + " for actionId: " + id);
                keyStringMap.put(key, id);
            }
        }
    }
}

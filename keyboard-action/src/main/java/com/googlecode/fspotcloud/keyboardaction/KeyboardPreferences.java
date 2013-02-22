package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@GwtCompatible
public class KeyboardPreferences {

    private final Logger logger = Logger.getLogger(KeyboardPreferences.class.getName());
    private final String[] allModes;
    private final Map<ActionKey, String> keyStringMap = newHashMap();
    private final Map<String, KeyboardBinding> bindingsMap = newHashMap();

    @Inject
    KeyboardPreferences(ModesProvider allModes) {
        this.allModes = allModes.getModes();
    }

    String get(String mode, KeyStroke keyStroke) {
        String result = keyStringMap.get(new ActionKey(mode, keyStroke));
        return result;
    }

    void bind(String id, KeyboardBinding binding) {
        bindingsMap.put(id, binding);
        for (String mode : allModes) {
            KeyStroke[] keys = binding.getKeys(mode);
            for (KeyStroke keyStroke : keys) {
                ActionKey key = new ActionKey(mode, keyStroke);
                logger.log(Level.FINEST, "In mode: " + mode + " mapping keystroke: " + key + " to action: " + id);
                String action = keyStringMap.put(key, id);
                if (action != null) {
                    throw new IllegalStateException("Key " + key + " was already bound to " + action);
                }
            }
        }
    }

    boolean isRelevant(String actionId, String mode) {
        return !(bindingsMap.get(actionId).getKeys(mode).length == 0);
    }

    KeyStroke[] getKeysForAction(String mode, String actionId) {
        return bindingsMap.get(actionId).getKeys(mode);
    }

    Set<String> allActions() {
        return bindingsMap.keySet();
    }

    public KeyStroke[] getDefaultKeysForAction(String id) {
        return bindingsMap.get(id).getDefaultKeys();

    }

    Set<String> allRelevantActions(String mode) {
        HashSet result = newHashSet();
        for (String actionId: allActions()) {
            if (isRelevant(actionId, mode)) {
                result.add(actionId);
            }
        }
        return result;
    }
}

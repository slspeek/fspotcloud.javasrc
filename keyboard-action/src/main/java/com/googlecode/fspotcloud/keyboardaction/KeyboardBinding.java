package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
public class KeyboardBinding {

    private final KeyStroke[] defaultKeys;
    private final Map<String, KeyStroke[]> overridesMap = newHashMap();
    private String[] modes;
    private List<String> modeList;

    KeyboardBinding(KeyStroke[] defaultKeys) {
        this.defaultKeys = defaultKeys;
    }

    public static KeyboardBinding bind(KeyStroke... keyStrokes) {
        KeyboardBinding keyboardBinding = new KeyboardBinding(keyStrokes);
        return keyboardBinding;
    }

    public KeyboardBinding override(String mode, KeyStroke... keyStrokes) {
        overridesMap.put(mode, keyStrokes);
        return this;
    }

    public KeyboardBinding withDefaultModes(String... modes) {
        this.modes = modes;
        modeList = newArrayList(modes);
        return this;
    }

    public KeyStroke[] getKeys(String mode) {
        KeyStroke[] result = {};
        if (modeList.contains(mode)) {
            result = defaultKeys;
        }
        KeyStroke[] override = overridesMap.get(mode);
        if (override != null) {
            result = override;
        }
        return result;
    }

    public String[] getModes() {
        return modes;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("defaultKeys", newArrayList(defaultKeys)).add("defaultModes", modeList).add("overrides", overridesMap).toString();
    }

    public KeyStroke[] getDefaultKeys() {
        return defaultKeys;
    }
}

package com.googlecode.fspotcloud.keyboardaction;

public class SimpleModesProvider implements ModesProvider {

    private final String[] modes;

    public SimpleModesProvider(String[] modes) {
        this.modes = modes;
    }

    @Override
    public String[] getModes() {
        return modes;
    }
}

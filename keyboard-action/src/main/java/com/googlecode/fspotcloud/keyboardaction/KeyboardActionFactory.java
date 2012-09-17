package com.googlecode.fspotcloud.keyboardaction;

public class KeyboardActionFactory {

    private final String[] modes;
    private final ActionImplementationRegister actionImplementationRegister = new ActionImplementationRegister();
    private final KeyboardPreferences keyboardPreferences;
    private ActionManager actionManager;
    private ConfigBuilder configBuilder;

    public KeyboardActionFactory(String[] modes) {
        this.modes = modes;
        keyboardPreferences = new KeyboardPreferences(modes);
        actionManager = new ActionManager(actionImplementationRegister, keyboardPreferences);
        configBuilder = new ConfigBuilder(actionImplementationRegister, keyboardPreferences);
    }


    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public IModeController getModeController() {
        return actionManager;
    }

}

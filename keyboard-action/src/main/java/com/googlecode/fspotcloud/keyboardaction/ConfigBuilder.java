package com.googlecode.fspotcloud.keyboardaction;

public class ConfigBuilder {

    private final ActionImplementationRegister actionImplementationRegister;
    private final KeyboardPreferences keyboardPreferences;

    public ConfigBuilder(ActionImplementationRegister actionImplementationRegister, KeyboardPreferences keyboardPreferences) {
        this.actionImplementationRegister = actionImplementationRegister;
        this.keyboardPreferences = keyboardPreferences;
    }

    public ConfigBuilder addBinding(ActionDef actionDef,
                             IActionHandler handler,
                             KeyboardBinding binding) {
        actionImplementationRegister.putAction(actionDef.getId(), handler);
        keyboardPreferences.bind(actionDef.getId(), binding);
        return this;
    }


}

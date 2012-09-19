package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

@GwtCompatible
public class ConfigBuilder {

    private final ActionImplementationRegister actionImplementationRegister;
    private final KeyboardPreferences keyboardPreferences;
    private final ButtonDefinitions buttonDefinitions;

    @Inject
    public ConfigBuilder(ActionImplementationRegister actionImplementationRegister, KeyboardPreferences keyboardPreferences, ButtonDefinitions buttonDefinitions) {
        this.actionImplementationRegister = actionImplementationRegister;
        this.keyboardPreferences = keyboardPreferences;
        this.buttonDefinitions = buttonDefinitions;
    }

    public ConfigBuilder addBinding(ActionDef actionDef,
                                    IActionHandler handler,
                                    KeyboardBinding binding) {
        actionImplementationRegister.putAction(actionDef.getId(), handler);
        keyboardPreferences.bind(actionDef.getId(), binding);
        buttonDefinitions.putAction(actionDef);
        return this;
    }


}

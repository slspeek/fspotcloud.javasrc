package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

@GwtCompatible
public class KeyboardActionFactory {


    private final ActionImplementationRegister actionImplementationRegister = new ActionImplementationRegister();
    private final KeyboardPreferences keyboardPreferences;
    private final ActionManager actionManager;
    private final ConfigBuilder configBuilder;
    private final ButtonDefinitions buttonDefinitions;
    private final IModeController modeController;
    private final NativePreviewHandler nativePreviewHandler;
    private final EventBus eventBus = new SimpleEventBus();

    public KeyboardActionFactory(String[] modes) {
        modeController = new ModeController(modes[0]);
        keyboardPreferences = new KeyboardPreferences(modes);
        buttonDefinitions = new ButtonDefinitions();
        actionManager = new ActionManager(actionImplementationRegister);
        eventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
        configBuilder = new ConfigBuilder(actionImplementationRegister, keyboardPreferences, buttonDefinitions);
        nativePreviewHandler = new NativePreviewHandler(eventBus,keyboardPreferences,modeController);
        nativePreviewHandler.init();
    }


    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public IModeController getModeController() {
        return modeController;
    }

    public ActionButton getButton(String actionId) {
        ActionDef actionDef = buttonDefinitions.getAction(actionId);
        return new ActionButton(actionDef, eventBus);
    }

}

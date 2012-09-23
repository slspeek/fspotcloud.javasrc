package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
public class KeyboardActionFactory {


    private final ActionImplementationRegister actionImplementationRegister = new ActionImplementationRegister();
    private final Map<String, ActionCategory> actionCategoryMap = newHashMap();
    private final KeyboardPreferences keyboardPreferences;
    private final ActionManager actionManager;
    private final ConfigBuilder configBuilder;
    private final ButtonDefinitions buttonDefinitions;
    private final IModeController modeController;
    private final NativePreviewHandler nativePreviewHandler;
    private final EventBus eventBus = new SimpleEventBus();
    private final HelpActions helpActions;

    private final String[] allModes;


    public KeyboardActionFactory(String[] modes) {
        this.allModes = modes;
        keyboardPreferences = new KeyboardPreferences(modes);
        buttonDefinitions = new ButtonDefinitions();
        actionManager = new ActionManager(actionImplementationRegister);
        eventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
        configBuilder = new ConfigBuilder(actionImplementationRegister, keyboardPreferences, buttonDefinitions, actionCategoryMap);
        modeController = new ModeController(modes[0], keyboardPreferences, eventBus);
        nativePreviewHandler = new NativePreviewHandler(eventBus, keyboardPreferences, modeController);
        nativePreviewHandler.init();
        helpActions = new HelpActions(allModes, configBuilder, keyboardPreferences, modeController);
        helpActions.initHelpActions();
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

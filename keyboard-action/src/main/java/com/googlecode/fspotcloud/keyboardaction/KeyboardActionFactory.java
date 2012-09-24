package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.core.shared.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class KeyboardActionFactory {


    private final ActionImplementationRegister actionImplementationRegister = new ActionImplementationRegister();
    private final List<ActionCategory> actionCategoryMap = newArrayList();
    private final KeyboardPreferences keyboardPreferences;
    private final ActionManager actionManager;
    private final ConfigBuilder configBuilder;
    private final ButtonDefinitions buttonDefinitions;
    private final IModeController modeController;
    private final NativePreviewHandler nativePreviewHandler;
    private final EventBus eventBus = new SimpleEventBus();
    private final HelpActions helpActions;
    private final HelpContentGenerator helpContentGenerator;
    private final Resources resources = GWT.create(Resources.class);

    private final String[] allModes;


    public KeyboardActionFactory(String[] modes) {
        resources.style().ensureInjected();
        this.allModes = modes;
        keyboardPreferences = new KeyboardPreferences(modes);
        this.helpContentGenerator = new HelpContentGenerator(resources, keyboardPreferences);
        buttonDefinitions = new ButtonDefinitions();
        actionManager = new ActionManager(actionImplementationRegister);
        eventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
        configBuilder = new ConfigBuilder(actionImplementationRegister, keyboardPreferences, buttonDefinitions, actionCategoryMap);
        modeController = new ModeController(modes[0], keyboardPreferences, eventBus);
        nativePreviewHandler = new NativePreviewHandler(eventBus, keyboardPreferences, modeController);
        nativePreviewHandler.init();
        helpActions = new HelpActions(allModes, configBuilder, keyboardPreferences, modeController, helpContentGenerator, resources);
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
        return new ActionButton(actionDef, eventBus, resources);
    }

    public ActionToolbar getToolBar() {
        return new ActionToolbar(resources);
    }
}

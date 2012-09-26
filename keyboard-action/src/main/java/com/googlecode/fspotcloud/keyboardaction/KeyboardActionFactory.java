package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;

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
    private final EventBus eventBus;
    private final HelpActions helpActions;
    private final HelpContentGenerator helpContentGenerator;
    private final KeyboardActionResources keyboardActionResources = GWT.create(KeyboardActionResources.class);

    private final String[] allModes;

    @Inject
    public KeyboardActionFactory(ModesProvider modesProvider, EventBus eventBus) {
        this.eventBus = eventBus;
        keyboardActionResources.style().ensureInjected();
        this.allModes = modesProvider.getModes();
        keyboardPreferences = new KeyboardPreferences(allModes);
        this.helpContentGenerator = new HelpContentGenerator(keyboardActionResources, keyboardPreferences);
        buttonDefinitions = new ButtonDefinitions();
        actionManager = new ActionManager(actionImplementationRegister);
        this.eventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
        configBuilder = new ConfigBuilder(actionImplementationRegister, keyboardPreferences, buttonDefinitions, actionCategoryMap);
        modeController = new ModeController(allModes[0], keyboardPreferences, this.eventBus);
        nativePreviewHandler = new NativePreviewHandler(this.eventBus, keyboardPreferences, modeController);
        nativePreviewHandler.init();
        helpActions = new HelpActions(allModes, configBuilder, keyboardPreferences, modeController, helpContentGenerator, keyboardActionResources);
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
        return new ActionButton(actionDef, eventBus, keyboardActionResources);
    }

    public ActionToolbar getToolBar() {
        return new ActionToolbar(keyboardActionResources);
    }
}

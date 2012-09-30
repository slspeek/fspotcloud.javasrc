package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import java.util.List;

@GwtCompatible
public class KeyboardActionFactory {


    private final ActionImplementationRegister actionImplementationRegister;
    private final List<ActionCategory> actionCategoryList;
    private final ActionManager actionManager;
    private final ConfigBuilder configBuilder;
    private final ButtonDefinitions buttonDefinitions;
    private final NativePreviewHandler nativePreviewHandler;
    private final EventBus eventBus;
    private final HelpActions helpActions;
    private final KeyboardActionResources keyboardActionResources = GWT.create(KeyboardActionResources.class);

    private IModeController modeController;

    @Inject
    public KeyboardActionFactory(ActionImplementationRegister actionImplementationRegister,
                                 EventBus eventBus,
                                 ActionManager actionManager,
                                 NativePreviewHandler nativePreviewHandler,
                                 HelpActions helpActions,
                                 ConfigBuilder configBuilder,
                                 IModeController modeController,
                                 ButtonDefinitions buttonDefinitions,
                                 List<ActionCategory> actionCategoryList
    ) {
        keyboardActionResources.style().ensureInjected();
        this.actionImplementationRegister = actionImplementationRegister;
        this.eventBus = eventBus;
        this.actionManager = actionManager;
        this.helpActions = helpActions;
        this.actionCategoryList = actionCategoryList;
        this.buttonDefinitions = buttonDefinitions;
        this.configBuilder = configBuilder;
        this.modeController = modeController;
        this.nativePreviewHandler = nativePreviewHandler;
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

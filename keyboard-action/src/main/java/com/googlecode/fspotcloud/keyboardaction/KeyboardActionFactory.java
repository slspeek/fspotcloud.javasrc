package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.List;

@GwtCompatible
public class KeyboardActionFactory {


    private final ActionHandlerRegistry actionHandlerRegistry;
    private final List<ActionCategory> actionCategoryList;
    private final ActionManager actionManager;
    private final ConfigBuilder configBuilder;
    private final ActionUIRegistry actionUIRegistry;
    private final NativePreviewHandler nativePreviewHandler;
    private final HelpActionsFactory helpActionsFactory;
    private final ButtonFactory buttonFactory;
    private final KeyboardActionResources keyboardActionResources;

    private IModeController modeController;

    @Inject
    public KeyboardActionFactory(UIRegistrationBuilder builder,
                                 ActionHandlerRegistry actionHandlerRegistry,
                                 ActionManager actionManager,
                                 NativePreviewHandler nativePreviewHandler,
                                 HelpActionsFactory helpActionsFactory,
                                 ConfigBuilder configBuilder,
                                 IModeController modeController,
                                 ActionUIRegistry actionUIRegistry,
                                 List<ActionCategory> actionCategoryList,
                                 ButtonFactory buttonFactory,
                                 KeyboardActionResources keyboardActionResources,
                                 ActionMenuResources menuResources,
                                 ActionButtonResources buttonResources) {
        this.keyboardActionResources = keyboardActionResources;
        builder.build();
        this.buttonFactory = buttonFactory;
        this.keyboardActionResources.style().ensureInjected();
        menuResources.style().ensureInjected();
        buttonResources.style().ensureInjected();
        this.actionHandlerRegistry = actionHandlerRegistry;
        this.actionManager = actionManager;
        this.helpActionsFactory = helpActionsFactory;
        this.actionCategoryList = actionCategoryList;
        this.actionUIRegistry = actionUIRegistry;
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
        return buttonFactory.getButton(actionId);
    }

    public ActionButton getButton(ActionUIDef actionUIDef) {
        return buttonFactory.getButton(actionUIDef);
    }

    public ActionToolbar getToolBar() {
        return new ActionToolbar(keyboardActionResources, buttonFactory);
    }

    public ActionMenu getMenu(String caption) {
        return buttonFactory.getMenu(caption);
    }
}

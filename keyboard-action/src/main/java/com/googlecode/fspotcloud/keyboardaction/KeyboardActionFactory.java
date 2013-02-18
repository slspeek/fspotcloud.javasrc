package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.core.shared.GWT;
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
    private final ActionButtonFactory actionButtonFactory;
    private final KeyboardActionResources keyboardActionResources = GWT.create(KeyboardActionResources.class);

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
                                 ActionButtonFactory actionButtonFactory) {
        builder.build();
        this.actionButtonFactory = actionButtonFactory;
        keyboardActionResources.style().ensureInjected();
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
        return actionButtonFactory.get(actionId);
    }

    public ActionButton getButton(ActionUIDef actionUIDef) {
        return actionButtonFactory.get(actionUIDef);
    }

    public ActionToolbar getToolBar() {
        return new ActionToolbar(keyboardActionResources, actionButtonFactory);
    }

    public ActionMenu getMenu(String caption) {
        return actionButtonFactory.getMenu(caption);
    }
}

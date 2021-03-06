package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.core.shared.GWT;
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
    private final HelpActionsFactory helpActionsFactory;
    private final ActionButtonFactory actionButtonFactory;
    private final KeyboardActionResources keyboardActionResources = GWT.create(KeyboardActionResources.class);

    private IModeController modeController;

    @Inject
    public KeyboardActionFactory(ActionImplementationRegister actionImplementationRegister,
                                 ActionManager actionManager,
                                 NativePreviewHandler nativePreviewHandler,
                                 HelpActionsFactory helpActionsFactory,
                                 ConfigBuilder configBuilder,
                                 IModeController modeController,
                                 ButtonDefinitions buttonDefinitions,
                                 List<ActionCategory> actionCategoryList,
                                 ActionButtonFactory actionButtonFactory) {
        this.actionButtonFactory = actionButtonFactory;
        keyboardActionResources.style().ensureInjected();
        this.actionImplementationRegister = actionImplementationRegister;
        this.actionManager = actionManager;
        this.helpActionsFactory = helpActionsFactory;
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
        return actionButtonFactory.get(actionId);
    }

    public ActionButton getButton(ActionDef actionDef) {
        return actionButtonFactory.get(actionDef);
    }

    public ActionToolbar getToolBar() {
        return new ActionToolbar(keyboardActionResources, actionButtonFactory);
    }

    public ActionMenu getMenu(String caption) {
        return actionButtonFactory.getMenu(caption);
    }
}

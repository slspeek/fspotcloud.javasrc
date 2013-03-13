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
    private final WidgetFactory widgetFactory;
    private final KeyboardActionResources keyboardActionResources;
    private final ResourcesSetup resourcesSetup;

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
                                 WidgetFactory widgetFactory,
                                 KeyboardActionResources keyboardActionResources,
                                 ResourcesSetup resourcesSetup) {
        this.keyboardActionResources = keyboardActionResources;
        this.resourcesSetup = resourcesSetup;
        resourcesSetup.ensureInjected();
        builder.build();
        this.widgetFactory = widgetFactory;
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
        return widgetFactory.getButton(actionId);
    }

    public ActionButton getButton(ActionUIDef actionUIDef) {
        return widgetFactory.getButton(actionUIDef);
    }

    public ActionToolbar getToolBar() {
        return new ActionToolbar(keyboardActionResources, widgetFactory);
    }

    public ActionMenu getMenu(String caption) {
        return widgetFactory.getMenu(caption);
    }
}

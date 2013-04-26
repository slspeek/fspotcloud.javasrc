package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.keyboardaction.gwt.*;

import java.util.List;

@GwtCompatible
public class KeyboardActionFactory {


    private final ActionHandlerRegistry actionHandlerRegistry;
    private final List<ActionCategory> actionCategoryList;
    private final IActionManager actionManager;
    private final ConfigBuilder configBuilder;
    private final ActionUIRegistry actionUIRegistry;
    private final NativePreviewHandler nativePreviewHandler;
    private final HelpActionsFactory helpActionsFactory;
    private final WidgetFactory widgetFactory;
    private final ActionToolbarResources actionToolbarResources;
    private final ResourcesSetup resourcesSetup;
    private final ToolbarFactory toolbarFactory;

    private IModeController modeController;

    @Inject
    public KeyboardActionFactory(UIRegistrationBuilder builder,
                                 ActionHandlerRegistry actionHandlerRegistry,
                                 IActionManager actionManager,
                                 NativePreviewHandler nativePreviewHandler,
                                 HelpActionsFactory helpActionsFactory,
                                 ConfigBuilder configBuilder,
                                 IModeController modeController,
                                 ActionUIRegistry actionUIRegistry,
                                 List<ActionCategory> actionCategoryList,
                                 WidgetFactory widgetFactory,
                                 ActionToolbarResources actionToolbarResources,
                                 ResourcesSetup resourcesSetup,
                                 ToolbarFactory toolbarFactory) {
        this.actionToolbarResources = actionToolbarResources;
        this.resourcesSetup = resourcesSetup;
        this.toolbarFactory = toolbarFactory;
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
        return getToolBar(actionToolbarResources);
    }

    public ActionToolbar getToolBar(ActionToolbarResources actionToolbarResources
    ) {
        return toolbarFactory.get(actionToolbarResources);
    }

    public ActionMenu getMenu(String caption) {
        return widgetFactory.getMenu(caption);
    }
}

package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.List;

@GwtCompatible
public class ConfigBuilder {

    private final ActionHandlerRegistry actionHandlerRegistry;
    private final KeyboardPreferences keyboardPreferences;
    private final ActionUIRegistry actionUIRegistry;
    private final List<ActionCategory> categoryList;

    @Inject
    private ConfigBuilder(ActionHandlerRegistry actionHandlerRegistry,
                          KeyboardPreferences keyboardPreferences,
                          ActionUIRegistry actionUIRegistry,
                          List<ActionCategory> categoryList) {
        this.actionHandlerRegistry = actionHandlerRegistry;
        this.keyboardPreferences = keyboardPreferences;
        this.actionUIRegistry = actionUIRegistry;
        this.categoryList = categoryList;
    }

    public ConfigBuilder register(ActionCategory actionCategory, ActionUIDef actionUIDef, KeyboardBinding keyboardBinding) {
        actionUIRegistry.putAction(actionUIDef);
        keyboardPreferences.bind(actionUIDef.getId(), keyboardBinding);
        actionCategory.add(actionUIDef);
        return this;
    }

    public ConfigBuilder addBinding(ActionCategory actionCategory,
                                    ActionUIDef actionUIDef,
                                    IActionHandler handler,
                                    KeyboardBinding binding) {
        bindHandler(actionUIDef, handler);
        register(actionCategory, actionUIDef, binding);
        return this;
    }

    public ConfigBuilder bindHandler(ActionUIDef actionUIDef, IActionHandler actionHandler) {
        actionHandlerRegistry.putAction(actionUIDef.getId(), actionHandler);
        return this;
    }


    public List<ActionCategory> getActionCategoryList() {
        return categoryList;
    }

    public ActionCategory createActionCategory(String name) {
        ActionCategory actionCategory = new ActionCategory(name);
        categoryList.add(actionCategory);
        return actionCategory;
    }

}

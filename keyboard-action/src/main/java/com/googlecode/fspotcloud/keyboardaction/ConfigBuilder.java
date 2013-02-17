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

    public ConfigBuilder register(ActionCategory actionCategory, ActionDef actionDef, KeyboardBinding keyboardBinding) {
        actionUIRegistry.putAction(actionDef);
        keyboardPreferences.bind(actionDef.getId(), keyboardBinding);
        actionCategory.add(actionDef);
        return this;
    }

    public ConfigBuilder addBinding(ActionCategory actionCategory,
                                    ActionDef actionDef,
                                    IActionHandler handler,
                                    KeyboardBinding binding) {
        bindHandler(actionDef, handler);
        register(actionCategory, actionDef, binding);
        return this;
    }

    public ConfigBuilder bindHandler(ActionDef actionDef, IActionHandler actionHandler) {
        actionHandlerRegistry.putAction(actionDef.getId(), actionHandler);
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

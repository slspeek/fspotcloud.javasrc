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


    public ConfigBuilder addBinding(ActionCategory actionCategory,
                                    ActionUIDef actionUIDef,
                                    IActionHandler handler,
                                    Relevance relevance
    ) {
        bindHandler(actionUIDef, handler);
        register(actionCategory, actionUIDef, relevance);
        return this;
    }

    public ConfigBuilder register(ActionCategory actionCategory, ActionUIDef actionUIDef, Relevance relevance) {
        actionUIRegistry.putAction(actionUIDef);
        keyboardPreferences.bind(actionUIDef.getId(), relevance);
        actionCategory.add(actionUIDef);
        return this;
    }

    public ConfigBuilder bindHandler(ActionUIDef actionUIDef, IActionHandler actionHandler) {
        actionHandlerRegistry.putAction(actionUIDef.getId(), actionHandler);
        return this;
    }


    public List<ActionCategory> getActionCategoryList() {
        return categoryList;
    }

    public ActionCategory createCategory(String name) {
        ActionCategory actionCategory = new ActionCategory(name);
        categoryList.add(actionCategory);
        return actionCategory;
    }

}

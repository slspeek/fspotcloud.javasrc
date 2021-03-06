package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.List;

@GwtCompatible
public class ConfigBuilder {

    private final ActionImplementationRegister actionImplementationRegister;
    private final KeyboardPreferences keyboardPreferences;
    private final ButtonDefinitions buttonDefinitions;
    private final List<ActionCategory> categoryList;

    @Inject
    private ConfigBuilder(ActionImplementationRegister actionImplementationRegister,
                          KeyboardPreferences keyboardPreferences,
                          ButtonDefinitions buttonDefinitions,
                          List<ActionCategory> categoryList) {
        this.actionImplementationRegister = actionImplementationRegister;
        this.keyboardPreferences = keyboardPreferences;
        this.buttonDefinitions = buttonDefinitions;
        this.categoryList = categoryList;
    }

    public ConfigBuilder register(ActionCategory actionCategory, ActionDef actionDef, KeyboardBinding keyboardBinding) {
        buttonDefinitions.putAction(actionDef);
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
        actionImplementationRegister.putAction(actionDef.getId(), actionHandler);
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

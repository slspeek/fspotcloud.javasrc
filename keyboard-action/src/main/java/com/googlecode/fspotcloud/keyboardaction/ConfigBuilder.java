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
    public ConfigBuilder(ActionImplementationRegister actionImplementationRegister,
                         KeyboardPreferences keyboardPreferences,
                         ButtonDefinitions buttonDefinitions,
                         List<ActionCategory> categoryList) {
        this.actionImplementationRegister = actionImplementationRegister;
        this.keyboardPreferences = keyboardPreferences;
        this.buttonDefinitions = buttonDefinitions;
        this.categoryList = categoryList;
    }

    public ConfigBuilder addBinding(ActionCategory actionCategory,
                                    ActionDef actionDef,
                                    IActionHandler handler,
                                    KeyboardBinding binding) {
        actionImplementationRegister.putAction(actionDef.getId(), handler);
        keyboardPreferences.bind(actionDef.getId(), binding);
        buttonDefinitions.putAction(actionDef);
        actionCategory.add(actionDef);
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

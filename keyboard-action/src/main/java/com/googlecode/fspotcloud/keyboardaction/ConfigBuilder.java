package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;

import java.util.List;
import java.util.Map;

@GwtCompatible
public class ConfigBuilder {

    private final ActionImplementationRegister actionImplementationRegister;
    private final KeyboardPreferences keyboardPreferences;
    private final ButtonDefinitions buttonDefinitions;


    private final List<ActionCategory> actionCategoryMap;

    public ConfigBuilder(ActionImplementationRegister actionImplementationRegister, KeyboardPreferences keyboardPreferences,
                         ButtonDefinitions buttonDefinitions,
                         List<ActionCategory> actionCategoryMap) {
        this.actionImplementationRegister = actionImplementationRegister;
        this.keyboardPreferences = keyboardPreferences;
        this.buttonDefinitions = buttonDefinitions;
        this.actionCategoryMap = actionCategoryMap;
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
        return actionCategoryMap;
    }

    public ActionCategory createActionCategory(String name) {
        ActionCategory actionCategory = new ActionCategory(name);
        actionCategoryMap.add(actionCategory);
        return actionCategory;
    }


}

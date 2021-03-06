package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
public class ButtonDefinitions {

    private final Map<String, ActionDef> registry = newHashMap();

    @Inject
    private ButtonDefinitions() {
    }

    void putAction(ActionDef actionDef) {
        registry.put(actionDef.getId(), actionDef);
    }

    ActionDef getAction(String actionKey) {
        return registry.get(actionKey);
    }


}

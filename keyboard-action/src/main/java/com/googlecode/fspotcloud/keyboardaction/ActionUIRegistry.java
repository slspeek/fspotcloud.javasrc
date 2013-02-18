package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
public class ActionUIRegistry {

    private final Map<String, ActionUIDef> registry = newHashMap();

    @Inject
    private ActionUIRegistry() {
    }

    void putAction(ActionUIDef actionUIDef) {
        registry.put(actionUIDef.getId(), actionUIDef);
    }

    ActionUIDef getAction(String actionKey) {
        return registry.get(actionKey);
    }


}

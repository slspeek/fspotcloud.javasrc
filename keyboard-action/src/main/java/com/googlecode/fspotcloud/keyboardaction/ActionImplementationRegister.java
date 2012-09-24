package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

@GwtCompatible
class ActionImplementationRegister {

    private final Map<String, IActionHandler> registry = newHashMap();

    void putAction(String actionId, IActionHandler handlerI) {
        IActionHandler previous = registry.put(actionId, handlerI);
        if (previous != null) {
            throw new IllegalStateException("Action " + actionId + " was already bound.");
        }
    }

    IActionHandler getAction(String actionKey) {
        return registry.get(actionKey);
    }


}

package com.googlecode.fspotcloud.keyboardaction;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

class ActionImplementationRegister {

    private final Map<String, IActionHandler> registry = newHashMap();

    void putAction(String actionId, IActionHandler handlerI) {
        registry.put(actionId, handlerI);
    }

    IActionHandler getAction(String actionKey) {
        return registry.get(actionKey);
    }


}

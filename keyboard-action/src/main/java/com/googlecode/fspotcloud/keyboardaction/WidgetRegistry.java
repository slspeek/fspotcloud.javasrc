package com.googlecode.fspotcloud.keyboardaction;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class WidgetRegistry {

    private final Map<String, Set<ActionWidget>> registry = newHashMap();

    public void add(String actionId, ActionWidget widget) {
        Set<ActionWidget> stored= registry.get(actionId);
        if (stored == null) {
            stored = newHashSet();
        }
        stored.add(widget);
        registry.put(actionId, stored);
    }

    public Set<ActionWidget> get(String actionId) {
        Set<ActionWidget> result = newHashSet();
        Set<ActionWidget> stored = registry.get(actionId);
        if (stored != null) {
            result.addAll(stored);
        }
        return result;
    }

}

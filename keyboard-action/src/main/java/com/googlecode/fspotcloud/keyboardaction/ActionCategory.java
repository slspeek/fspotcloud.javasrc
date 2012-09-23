package com.googlecode.fspotcloud.keyboardaction;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ActionCategory {
    private final String name;
    private final List<ActionDef> actions= newArrayList();

    public String getName() {
        return name;
    }

    void add(ActionDef action) {
        actions.add(action);
    }

    List<ActionDef> getActions() {
        return actions;
    }

    ActionCategory(String name) {
        this.name = name;

    }
}

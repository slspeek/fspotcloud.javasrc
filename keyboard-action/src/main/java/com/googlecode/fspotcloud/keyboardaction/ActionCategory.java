package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class ActionCategory {
    private final String name;
    private final List<ActionDef> actions = newArrayList();

    ActionCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void add(ActionDef action) {
        actions.add(action);
    }

    List<ActionDef> getActions() {
        return actions;
    }

}

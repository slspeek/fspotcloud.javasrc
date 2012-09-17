package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.user.client.ui.Image;

import java.util.List;

public class ActionDef {

    private final String id, name, description;

    public ActionDef(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

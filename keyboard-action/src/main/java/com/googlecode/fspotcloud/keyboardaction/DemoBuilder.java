package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class DemoBuilder {

    private final ButtonDefinitions buttonDefinitions;
    private final EventBus eventBus;



    public DemoBuilder(ButtonDefinitions buttonDefinitions, EventBus eventBus) {
        this.buttonDefinitions = buttonDefinitions;
        this.eventBus = eventBus;
    }
}

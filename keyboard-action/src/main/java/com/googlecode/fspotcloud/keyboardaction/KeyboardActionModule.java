package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.AbstractModule;
import com.google.web.bindery.event.shared.EventBus;

public class KeyboardActionModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(KeyboardActionFactory.class);
    }
}

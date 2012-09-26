package com.googlecode.fspotcloud.testharness;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionModule;
import com.googlecode.fspotcloud.keyboardaction.ModesProvider;
import com.googlecode.fspotcloud.keyboardaction.SimpleModesProvider;

public class TestharnessModule extends AbstractGinModule {

    @Override
    public void configure() {
        bind(EventBus.class).to(SimpleEventBus.class);
        install(new KeyboardActionModule());
    }

    @Provides
    ModesProvider getModes() {
        return new SimpleModesProvider(MainFactory.MODES);
    }
}

package com.googlecode.fspotcloud.testharness;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionModule;
import com.googlecode.fspotcloud.keyboardaction.ModesProvider;
import com.googlecode.fspotcloud.keyboardaction.SimpleModesProvider;
import com.googlecode.fspotcloud.keyboardaction.UIRegistrationBuilder;

public class TestharnessModule extends AbstractGinModule {

    @Override
    public void configure() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(UIRegistrationBuilder.class).to(MainBuilder.class);
        install(new KeyboardActionModule());
    }

    @Provides
    ModesProvider getModes() {
        return new SimpleModesProvider(MainBuilder.MODES);
    }
}

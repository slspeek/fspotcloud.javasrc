package com.googlecode.fspotcloud.testharness;

import com.google.code.ginmvp.client.GinMvpModule;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.keyboardaction.*;

public class TestharnessModule extends AbstractGinModule {

    @Override
    public void configure() {

        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(UIRegistrationBuilder.class).to(MainBuilder.class);
        bind(ActionToolbar.class).toProvider(ToolbarProvider.class);
        bind(HomeView.class).to(HomeViewImpl.class).in(Singleton.class);
        bind(HomeView.HomePresenter.class).to(HomeActivity.class).in(Singleton.class);
        bind(OutView.class).to(OutViewImpl.class).in(Singleton.class);
        bind(OutView.OutPresenter.class).to(OutActivity.class).in(Singleton.class);
        install(new KeyboardActionModule());
        install(new GinMvpModule(HarnessActivityMapper.class, HomePlace.class, MvpDisplay.class, HarnessPlaceHistoryMapper.class));
    }

    @Provides
    ModesProvider getModes() {
        return new SimpleModesProvider(MainBuilder.MODES);
    }
}

package com.googlecode.fspotcloud.testharness;

import com.google.code.ginmvp.client.GinMvpModule;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionModule;
import com.googlecode.fspotcloud.keyboardaction.UIRegistrationBuilder;

public class TestharnessModule extends AbstractGinModule {

    @Override
    public void configure() {
        install(new GinMvpModule(
                HarnessActivityMapper.class,
                HomePlace.class,
                MvpDisplay.class,
                HarnessPlaceHistoryMapper.class));
        bind(UIRegistrationBuilder.class).to(MainBuilder.class);
        bind(ActionToolbar.class).toProvider(ToolbarProvider.class);
        bind(HomeView.class).to(HomeViewImpl.class).in(Singleton.class);
        bind(HomeView.HomePresenter.class).to(HomeActivity.class).in(Singleton.class);
        bind(OutView.class).to(OutViewImpl.class).in(Singleton.class);
        bind(OutView.OutPresenter.class).to(OutActivity.class).in(Singleton.class);
        install(new KeyboardActionModule());
    }
}

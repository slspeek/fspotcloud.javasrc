package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class KeyboardActionModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(IModeController.class).to(ModeController.class).in(Singleton.class);
        bind(ActionManager.class).toProvider(ActionManagerFactory.class).in(Singleton.class);
        bind(KeyboardActionFactory.class).in(Singleton.class);
        bind(ButtonDefinitions.class).in(Singleton.class);
        bind(NativePreviewHandler.class).in(Singleton.class);
        bind(KeyboardPreferences.class).in(Singleton.class);
        bind(ActionImplementationRegister.class).in(Singleton.class);
        bind(DemoBuilder.class);
        bind(DemoBuilderFactory.class);
        bind(HelpPopup.class);
        bind(HelpActions.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public List<ActionCategory> getActionCategoryList() {
        List<ActionCategory> r = newArrayList();
        return r;
    }
}

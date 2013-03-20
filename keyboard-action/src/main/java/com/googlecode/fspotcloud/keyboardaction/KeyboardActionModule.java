package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.keyboardaction.gwt.TwoColumnHelpPopup;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class KeyboardActionModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(IPlaceController.class).to(PlaceControllerImpl.class);
        bind(IModeController.class).toProvider(ModeControllerProvider.class).in(Singleton.class);
        bind(IActionManager.class).toProvider(ActionManagerFactory.class).in(Singleton.class);
        bind(ConfigBuilder.class).in(Singleton.class);
        bind(KeyboardActionFactory.class).asEagerSingleton();
        bind(ActionUIRegistry.class).in(Singleton.class);
        bind(NativePreviewHandler.class).in(Singleton.class);
        bind(KeyboardPreferences.class).in(Singleton.class);
        bind(ActionHandlerRegistry.class).in(Singleton.class);
        bind(DemoBuilder.class);
        bind(DemoBuilderFactory.class).in(Singleton.class);
        bind(WidgetRegistry.class).in(Singleton.class);
        bind(TwoColumnHelpPopup.class);
        bind(HelpActionsFactory.class).in(Singleton.class);
        bind(PlaceContext.class).toProvider(PlaceContextProvider.class);
        install(new GinFactoryModuleBuilder().build(HelpContentGeneratorFactory.class));
    }

    @Provides
    @Singleton
    public List<ActionCategory> getActionCategoryList() {
        List<ActionCategory> r = newArrayList();
        return r;
    }

    @Provides
    @Singleton
    public List<Demo> getDemoList() {
        List<Demo> r = newArrayList();
        return r;
    }
}

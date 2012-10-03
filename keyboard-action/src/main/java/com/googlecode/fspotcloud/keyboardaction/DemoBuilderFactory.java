package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DemoBuilderFactory {

    private final Provider<DemoBuilder> demoBuilderProvider;
    private final Provider<DemoPopup> demoPopupProvider;
    private final EventBus eventBus;

    @Inject
    public DemoBuilderFactory(Provider<DemoBuilder> demoBuilderProvider, Provider<DemoPopup> demoPopupProvider, EventBus eventBus) {
        this.demoBuilderProvider = demoBuilderProvider;
        this.demoPopupProvider = demoPopupProvider;
        this.eventBus = eventBus;
    }

    public DemoBuilder get(ActionDef actionDef) {
        DemoBuilder demoBuilder = demoBuilderProvider.get();
        demoBuilder.setDemo(new Demo(demoPopupProvider.get(), actionDef, eventBus));
        return demoBuilder;
    }
}

package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DemoBuilderFactory {

    private final Provider<DemoBuilder> demoBuilderProvider;
    private final Provider<HelpPopup> helpPopupProvider;

    @Inject
    public DemoBuilderFactory(Provider<DemoBuilder> demoBuilderProvider, Provider<HelpPopup> helpPopupProvider) {
        this.demoBuilderProvider = demoBuilderProvider;
        this.helpPopupProvider = helpPopupProvider;
    }

    public DemoBuilder get(ActionDef actionDef) {
        DemoBuilder demoBuilder = demoBuilderProvider.get();
        demoBuilder.setDemo(new Demo(helpPopupProvider.get(), actionDef));
        return demoBuilder;
    }
}

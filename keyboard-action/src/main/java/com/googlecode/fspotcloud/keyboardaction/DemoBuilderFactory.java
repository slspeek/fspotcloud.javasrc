package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.google.inject.Provider;

import java.util.List;

public class DemoBuilderFactory {

    private final Provider<DemoBuilder> demoBuilderProvider;
    private final Provider<DemoPopup> demoPopupProvider;
    private final List<Demo> demoList;
    private final EventBus eventBus;

    @Inject
    private DemoBuilderFactory(Provider<DemoBuilder> demoBuilderProvider,
                               Provider<DemoPopup> demoPopupProvider,
                               List<Demo> demoList, EventBus eventBus) {
        this.demoBuilderProvider = demoBuilderProvider;
        this.demoPopupProvider = demoPopupProvider;
        this.demoList = demoList;
        this.eventBus = eventBus;
    }

    public DemoBuilder get(ActionDef actionDef) {
        DemoBuilder demoBuilder = demoBuilderProvider.get();
        final Demo demo = new Demo(demoPopupProvider.get(), actionDef, eventBus);
        demoList.add(demo);
        demoBuilder.setDemo(demo);
        return demoBuilder;
    }

    public IActionHandler getStopDemoHandler() {
        IActionHandler actionHandler = new IActionHandler() {
            @Override
            public void performAction(String actionId) {
                for (Demo demo : demoList) {
                    demo.stop();
                }
            }
        };
        return actionHandler;
    }
}

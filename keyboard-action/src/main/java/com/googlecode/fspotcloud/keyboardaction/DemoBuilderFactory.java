package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

import java.util.List;

public class DemoBuilderFactory {

    private final Provider<DemoBuilder> demoBuilderProvider;
    private final Provider<Demo> demoProvider;
    private final List<Demo> demoList;


    @Inject
    private DemoBuilderFactory(Provider<DemoBuilder> demoBuilderProvider,
                               Provider<Demo> demoProvider,
                               List<Demo> demoList, EventBus eventBus) {
        this.demoBuilderProvider = demoBuilderProvider;
        this.demoProvider = demoProvider;
        this.demoList = demoList;
    }

    public DemoBuilder get(ActionUIDef actionUIDef) {
        DemoBuilder demoBuilder = demoBuilderProvider.get();
        final Demo demo = demoProvider.get().withActionUIDef(actionUIDef);
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

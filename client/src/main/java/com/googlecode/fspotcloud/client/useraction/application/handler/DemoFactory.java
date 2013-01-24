package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.client.useraction.raster.RasterActions;
import com.googlecode.fspotcloud.keyboardaction.DemoBuilder;
import com.googlecode.fspotcloud.keyboardaction.DemoBuilderFactory;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class DemoFactory {

    private final DemoBuilderFactory demoBuilderFactory;
    private final ApplicationActions applicationActions;
    private final NavigationActions navigationActions;
    private final RasterActions rasterActions;

    @Inject
    public DemoFactory(DemoBuilderFactory demoBuilderFactory,
                       ApplicationActions applicationActions,
                       NavigationActions navigationActions,
                       RasterActions rasterActions) {
        this.demoBuilderFactory = demoBuilderFactory;
        this.applicationActions = applicationActions;
        this.navigationActions = navigationActions;
        this.rasterActions = rasterActions;
    }

   public IActionHandler getDemo() {
        DemoBuilder builder = demoBuilderFactory.get(applicationActions.demo);
        builder.addStep(navigationActions.home, 3000);
        builder.addStep(rasterActions.toggle_tabular_view, 4000);
        builder.addStep(navigationActions.next, 3000);
        builder.addStep(rasterActions.toggle_tabular_view,  3000);
        builder.addStep(navigationActions.page_down, 3000);
        return builder.getDemo();
    }

}

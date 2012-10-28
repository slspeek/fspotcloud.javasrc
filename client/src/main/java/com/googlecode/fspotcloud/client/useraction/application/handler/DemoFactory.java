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

    @Inject
    public DemoFactory(DemoBuilderFactory demoBuilderFactory) {
        this.demoBuilderFactory = demoBuilderFactory;
    }

   public IActionHandler getDemo() {
        DemoBuilder builder = demoBuilderFactory.get(ApplicationActions.DEMO);
        builder.addStep(NavigationActions.HOME_DEF, 3000);
        builder.addStep(RasterActions.TOGGLE_TABULAR_VIEW, 4000);
        builder.addStep(NavigationActions.NEXT_DEF, 3000);
        builder.addStep(RasterActions.TOGGLE_TABULAR_VIEW,  3000);
        builder.addStep(NavigationActions.PAGE_DOWN_DEF, 3000);


        return builder.getDemo();
    }

}

package com.googlecode.fspotcloud.client.enduseraction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.enduseraction.about.AboutActions;
import com.googlecode.fspotcloud.client.enduseraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.enduseraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.client.enduseraction.raster.RasterActions;
import com.googlecode.fspotcloud.client.enduseraction.slideshow.SlideshowActions;
import com.googlecode.fspotcloud.client.main.ui.ToolbarButtonResources;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class MainToolbarProvider implements Provider<ActionToolbar> {


    private final KeyboardActionFactory keyboardActionFactory;
    private final ToolbarButtonResources toolbarButtonResources;
    private final AboutActions aboutActions;
    private final ApplicationActions applicationActions;
    private final NavigationActions navigationActions;
    private final SlideshowActions slideshowActions;
    private final RasterActions rasterActions;


    @Inject
    public MainToolbarProvider(KeyboardActionFactory keyboardActionFactory,
                               ToolbarButtonResources toolbarButtonResources,
                               AboutActions aboutActions,
                               ApplicationActions applicationActions,
                               NavigationActions navigationActions,
                               SlideshowActions slideshowActions,
                               RasterActions rasterActions) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.toolbarButtonResources = toolbarButtonResources;
        this.aboutActions = aboutActions;
        this.applicationActions = applicationActions;
        this.navigationActions = navigationActions;
        this.slideshowActions = slideshowActions;
        this.rasterActions = rasterActions;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();
        toolbar.setActionButtonResources(toolbarButtonResources);
        toolbar.setButtonStylePrimaryName("toolbar-button");
        toolbar.add(applicationActions.show_help);
        toolbar.add(applicationActions.demo);
        toolbar.add(navigationActions.home);
        toolbar.add(navigationActions.page_up);
        toolbar.add(navigationActions.back);
        toolbar.add(slideshowActions.slideshow_start);
        toolbar.add(navigationActions.next);
        toolbar.add(navigationActions.page_down);
        toolbar.add(navigationActions.end);
        toolbar.add(applicationActions.zoom_in);
        toolbar.add(applicationActions.zoom_out);
        toolbar.add(rasterActions.mail_fullsize);
        toolbar.add(applicationActions.login);
        toolbar.add(applicationActions.logout);
        toolbar.add(applicationActions.about);
        toolbar.add(aboutActions.project_hosting);
        toolbar.add(applicationActions.dashboard);
        toolbar.add(navigationActions.rss_feed);
        return toolbar;
    }
}

package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.useraction.about.AboutActions;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.client.useraction.raster.RasterActions;
import com.googlecode.fspotcloud.client.useraction.slideshow.SlideshowActions;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class MainToolbarProvider implements Provider<ActionToolbar> {


    private final KeyboardActionFactory keyboardActionFactory;
    private final AboutActions aboutActions;
    private final ApplicationActions applicationActions;
    private final NavigationActions navigationActions;
    private final SlideshowActions slideshowActions;
    private final RasterActions rasterActions;


    @Inject
    public MainToolbarProvider(UserActionFactory actionFactory,
                               KeyboardActionFactory keyboardActionFactory,
                               AboutActions aboutActions,
                               ApplicationActions applicationActions,
                               NavigationActions navigationActions,
                               SlideshowActions slideshowActions,
                               RasterActions rasterActions) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.aboutActions = aboutActions;
        this.applicationActions = applicationActions;
        this.navigationActions = navigationActions;
        this.slideshowActions = slideshowActions;
        this.rasterActions = rasterActions;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();

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

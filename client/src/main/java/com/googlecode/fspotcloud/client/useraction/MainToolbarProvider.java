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

    @Inject
    public MainToolbarProvider(UserActionFactory actionFactory,
                               KeyboardActionFactory keyboardActionFactory
    ) {
        this.keyboardActionFactory = keyboardActionFactory;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();

        toolbar.add(ApplicationActions.SHOW_HELP);
        toolbar.add(ApplicationActions.DEMO);
        toolbar.add(NavigationActions.HOME_DEF);
        toolbar.add(NavigationActions.PAGE_UP_DEF);
        toolbar.add(NavigationActions.BACK_DEF);
        toolbar.add(SlideshowActions.SLIDESHOW_START);
        toolbar.add(NavigationActions.NEXT_DEF);
        toolbar.add(NavigationActions.PAGE_DOWN_DEF);
        toolbar.add(NavigationActions.END_DEF);
        toolbar.add(ApplicationActions.ZOOM_IN);
        toolbar.add(ApplicationActions.ZOOM_OUT);
        toolbar.add(RasterActions.MAIL_FULLSIZE);
        toolbar.add(ApplicationActions.LOGIN);
        toolbar.add(ApplicationActions.LOGOUT);
        toolbar.add(ApplicationActions.ABOUT);
        toolbar.add(AboutActions.PROJECT_HOSTING);
        toolbar.add(ApplicationActions.DASHBOARD);
        return toolbar;
    }
}

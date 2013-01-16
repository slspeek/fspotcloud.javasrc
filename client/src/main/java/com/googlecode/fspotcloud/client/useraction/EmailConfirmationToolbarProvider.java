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

public class EmailConfirmationToolbarProvider implements Provider<ActionToolbar> {


    private final KeyboardActionFactory keyboardActionFactory;

    @Inject
    public EmailConfirmationToolbarProvider(UserActionFactory actionFactory,
                                            KeyboardActionFactory keyboardActionFactory
    ) {
        this.keyboardActionFactory = keyboardActionFactory;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();
        toolbar.add(ApplicationActions.LOGIN);
        return toolbar;
    }
}

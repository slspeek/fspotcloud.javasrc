package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StylesSetup {

    private final Logger log = Logger.getLogger(StylesSetup.class.getName());
    private final Resources resources;
    private final UserPagesResources userPagesResources;
    private final FadeAnimationResources fadeAnimationResources;
    private final AdminResources adminResources;
    private final HomeResources homeResources;
    private final PushButtonResources pushButtonResources;
    private final BigPushButtonResources bigPushButtonResources;
    private final AdminTreeResources adminTreeResources;
    private final BasicTreeResources basicTreeResources;
    private final BigActionButtonResources bigActionButtonResources;
    private final AdminActionButtonResources adminActionButtonResources;

    @Inject
    public StylesSetup(Resources resources,
                       UserPagesResources userPagesResources,
                       FadeAnimationResources fadeAnimationResources,
                       AdminResources adminResources,
                       HomeResources homeResources,
                       PushButtonResources pushButtonResources,
                       BigPushButtonResources bigPushButtonResources,
                       AdminTreeResources adminTreeResources,
                       BasicTreeResources basicTreeResources,
                       BigActionButtonResources bigActionButtonResources,
                       AdminActionButtonResources adminActionButtonResources) {
        this.resources = resources;
        this.userPagesResources = userPagesResources;
        this.fadeAnimationResources = fadeAnimationResources;
        this.adminResources = adminResources;
        this.homeResources = homeResources;
        this.pushButtonResources = pushButtonResources;
        this.bigPushButtonResources = bigPushButtonResources;
        this.adminTreeResources = adminTreeResources;
        this.basicTreeResources = basicTreeResources;
        this.bigActionButtonResources = bigActionButtonResources;
        this.adminActionButtonResources = adminActionButtonResources;
    }

    public void injectStyles() {
        resources.style().ensureInjected();
        userPagesResources.style().ensureInjected();
        fadeAnimationResources.style().ensureInjected();
        adminResources.style().ensureInjected();
        homeResources.style().ensureInjected();
        pushButtonResources.style().ensureInjected();
        bigPushButtonResources.style().ensureInjected();
        adminTreeResources.cellTreeStyle().ensureInjected();
        basicTreeResources.cellTreeStyle().ensureInjected();
        bigPushButtonResources.style().ensureInjected();
        bigActionButtonResources.style().ensureInjected();
        adminActionButtonResources.style().ensureInjected();
        log.log(Level.FINE, "all styles were injected");
    }


}

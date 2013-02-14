package com.googlecode.fspotcloud.client.main.ui;

import com.google.inject.Inject;

public class StylesSetup {

    private final Resources resources;
    private final UserPagesResources userPagesResources;
    private final FadeAnimationResources fadeAnimationResources;
    private final AdminResources adminResources;
    private final HomeResources homeResources;
    private final PushButtonResources pushButtonResources;
    private final BigPushButtonResources bigPushButtonResources;
    private final AdminTreeResources adminTreeResources;
    private final BasicTreeResources basicTreeResources;

    @Inject
    public StylesSetup(Resources resources,
                       UserPagesResources userPagesResources,
                       FadeAnimationResources fadeAnimationResources,
                       AdminResources adminResources,
                       HomeResources homeResources,
                       PushButtonResources pushButtonResources,
                       BigPushButtonResources bigPushButtonResources,
                       AdminTreeResources adminTreeResources,
                       BasicTreeResources basicTreeResources) {
        this.resources = resources;
        this.userPagesResources = userPagesResources;
        this.fadeAnimationResources = fadeAnimationResources;
        this.adminResources = adminResources;
        this.homeResources = homeResources;
        this.pushButtonResources = pushButtonResources;
        this.bigPushButtonResources = bigPushButtonResources;
        this.adminTreeResources = adminTreeResources;
        this.basicTreeResources = basicTreeResources;
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
    }


}

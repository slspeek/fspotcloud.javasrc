package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.resources.client.CssResource;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.ui.AdminResources;

public class StylesSetup {

    private final Resources resources;
    private final UserPagesResources userPagesResources;
    private final FadeAnimationResources fadeAnimationResources;
    private final AdminResources adminResources; //necessary
    private final HomeResources homeResources;
    private final PushButtonResources pushButtonResources;

    @Inject
    public StylesSetup(Resources resources,
                       UserPagesResources userPagesResources,
                       FadeAnimationResources fadeAnimationResources,
                       AdminResources adminResources,
                       HomeResources homeResources,
                       PushButtonResources pushButtonResources) {
        this.resources = resources;
        this.userPagesResources = userPagesResources;
        this.fadeAnimationResources = fadeAnimationResources;
        this.adminResources = adminResources;
        this.homeResources = homeResources;
        this.pushButtonResources = pushButtonResources;
    }

    public void injectStyles() {
        resources.style().ensureInjected();
        userPagesResources.style().ensureInjected();
        fadeAnimationResources.style().ensureInjected();
        adminResources.style().ensureInjected();
        homeResources.style().ensureInjected();
        pushButtonResources.style().ensureInjected();
    }


}

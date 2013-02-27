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
    private final AdminTreeResources adminTreeResources;
    private final BasicTreeResources basicTreeResources;
    private final BigActionButtonResources bigActionButtonResources;
    private final AdminActionButtonResources adminActionButtonResources;
    private final CellTableResources cellTableResources;

    @Inject
    public StylesSetup(Resources resources,
                       UserPagesResources userPagesResources,
                       FadeAnimationResources fadeAnimationResources,
                       AdminResources adminResources,
                       HomeResources homeResources,
                       AdminTreeResources adminTreeResources,
                       BasicTreeResources basicTreeResources,
                       BigActionButtonResources bigActionButtonResources,
                       AdminActionButtonResources adminActionButtonResources,
                       CellTableResources cellTableResources) {
        this.resources = resources;
        this.userPagesResources = userPagesResources;
        this.fadeAnimationResources = fadeAnimationResources;
        this.adminResources = adminResources;
        this.homeResources = homeResources;
        this.adminTreeResources = adminTreeResources;
        this.basicTreeResources = basicTreeResources;
        this.bigActionButtonResources = bigActionButtonResources;
        this.adminActionButtonResources = adminActionButtonResources;
        this.cellTableResources = cellTableResources;
    }

    public void injectStyles() {
        resources.style().ensureInjected();
        userPagesResources.style().ensureInjected();
        fadeAnimationResources.style().ensureInjected();
        adminResources.style().ensureInjected();
        homeResources.style().ensureInjected();
        adminTreeResources.cellTreeStyle().ensureInjected();
        basicTreeResources.cellTreeStyle().ensureInjected();
        bigActionButtonResources.style().ensureInjected();
        adminActionButtonResources.style().ensureInjected();
        cellTableResources.cellTableStyle().ensureInjected();
        log.log(Level.FINE, "all styles were injected");
    }


}

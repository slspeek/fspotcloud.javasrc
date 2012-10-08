package com.googlecode.fspotcloud.client.useraction.about;

import com.google.gwt.core.client.GWT;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class AboutActions {

    private static final Resources RESOURCES = GWT.create(Resources.class);

    public static final ActionDef LICENSE = new ActionDef("license",
            "License",
            "View the license",
            RESOURCES.licenceIcon());

    public static final ActionDef PROJECT_HOSTING = new ActionDef("project-hosting",
            "Project site",
            "Go to the site on Google Project Hosting",
            RESOURCES.projectSiteIcon());

    public static final ActionDef F_SPOT = new ActionDef("f-spot",
            "F-Spot",
            "Go to the F-Spot site",
            RESOURCES.fspotIcon());

    public static final ActionDef PROTON = new ActionDef("proton",
            "Proton radio",
            "Go to the Proton site",
            RESOURCES.protonIcon());

    public static final ActionDef STEVEN = new ActionDef("steven",
            "Authors website",
            "Go to the authors website",
            RESOURCES.authorIcon());

}

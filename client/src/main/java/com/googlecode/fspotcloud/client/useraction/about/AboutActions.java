package com.googlecode.fspotcloud.client.useraction.about;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class AboutActions {

    private final Resources RESOURCES;

    public final ActionDef license;

    public final ActionDef project_hosting;

    public final ActionDef f_spot;

    public final ActionDef proton;

    public final ActionDef build_server;

    @Inject
    public AboutActions(Resources RESOURCES) {
        this.RESOURCES = RESOURCES;
        license = new ActionDef("license",
                "License",
                "View the license",
                RESOURCES.licenceIcon());
        project_hosting = new ActionDef("project-hosting",
                "Project site",
                "Go to the site on Google Project Hosting",
                RESOURCES.projectSiteIcon());
        f_spot = new ActionDef("f-spot",
                "F-Spot",
                "Go to the F-Spot site",
                RESOURCES.fspotIcon());
        proton = new ActionDef("proton",
                "Proton radio",
                "Go to the Proton Radio site",
                RESOURCES.protonIcon());
        build_server = new ActionDef("build-server",
                "Build server",
                "Go to the Jenkins build server",
                RESOURCES.buildServerIcon());
    }


}

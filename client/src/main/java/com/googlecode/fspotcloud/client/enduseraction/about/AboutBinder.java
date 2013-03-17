package com.googlecode.fspotcloud.client.enduseraction.about;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.about.handler.*;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.Relevance;

import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;

public class AboutBinder extends AbstractBinder {

    private final BuildServerHandler buildServerHandler;
    private final FSpotHandler fSpotHandler;
    private final LicenseHandler licenseHandler;
    private final ProjectSiteHandler projectSiteHandler;
    private final ProtonHandler protonHandler;
    private final AboutActions aboutActions;

    @Inject
    public AboutBinder(
            CategoryDef categoryDef,
            BuildServerHandler buildServerHandler,
            FSpotHandler fSpotHandler,
            LicenseHandler licenseHandler,
            ProjectSiteHandler projectSiteHandler,
            ProtonHandler protonHandler,
            AboutActions aboutActions) {
        super(categoryDef.ABOUT);
        this.buildServerHandler = buildServerHandler;
        this.fSpotHandler = fSpotHandler;
        this.licenseHandler = licenseHandler;
        this.projectSiteHandler = projectSiteHandler;
        this.protonHandler = protonHandler;
        this.aboutActions = aboutActions;
    }


    @Override
    public void build() {
        bind(aboutActions.project_hosting, projectSiteHandler, get(alt('J')));
        bind(aboutActions.build_server, buildServerHandler, get(KeyStroke.B));
        bind(aboutActions.license, licenseHandler, get(KeyStroke.L));
        bind(aboutActions.f_spot, fSpotHandler, get(KeyStroke.U));
        bind(aboutActions.proton, protonHandler, get(KeyStroke.P));
    }

    private Relevance get(KeyStroke stroke) {
        return new Relevance(BasePlace.class).addDefaultKeys(stroke);
    }

}

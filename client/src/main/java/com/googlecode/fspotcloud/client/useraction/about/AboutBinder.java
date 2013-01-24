package com.googlecode.fspotcloud.client.useraction.about;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.about.handler.*;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

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
        bind(aboutActions.project_hosting, projectSiteHandler, get('J'));
        bind(aboutActions.build_server, buildServerHandler, get('B'));
        bind(aboutActions.license, licenseHandler, get('L'));
        bind(aboutActions.f_spot, fSpotHandler, get('U'));
        bind(aboutActions.proton, protonHandler, get('P'));
    }

    private KeyboardBinding get(char c) {
        return KeyboardBinding.bind(new KeyStroke(c)).withDefaultModes(Modes.ABOUT, Modes.TAG_VIEW, Modes.TREE_VIEW);
    }

}

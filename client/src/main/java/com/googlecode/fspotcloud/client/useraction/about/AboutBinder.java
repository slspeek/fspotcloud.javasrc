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

    @Inject
    public AboutBinder(
            CategoryDef categoryDef,
            BuildServerHandler buildServerHandler,
            FSpotHandler fSpotHandler,
            LicenseHandler licenseHandler,
            ProjectSiteHandler projectSiteHandler,
            ProtonHandler protonHandler) {
        super(categoryDef.ABOUT);

        this.buildServerHandler = buildServerHandler;
        this.fSpotHandler = fSpotHandler;
        this.licenseHandler = licenseHandler;
        this.projectSiteHandler = projectSiteHandler;
        this.protonHandler = protonHandler;
    }


    @Override
    public void build() {
         bind(AboutActions.PROJECT_HOSTING, projectSiteHandler, get('J'));
         bind(AboutActions.BUILD_SERVER, buildServerHandler, get('B'));
         bind(AboutActions.LICENSE, licenseHandler, get('L'));
         bind(AboutActions.F_SPOT, fSpotHandler, get('U'));
         bind(AboutActions.PROTON, protonHandler, get('P'));
    }

    private KeyboardBinding get(char c) {
        return KeyboardBinding.bind(new KeyStroke(c)).withDefaultModes(Modes.ABOUT, Modes.TAG_VIEW, Modes.TREE_VIEW);
    }

}

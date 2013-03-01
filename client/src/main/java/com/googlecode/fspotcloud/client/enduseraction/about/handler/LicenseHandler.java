package com.googlecode.fspotcloud.client.enduseraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;

public class LicenseHandler extends LoadNewLocationAction {

    @Inject
    private LicenseHandler(OpenNewTab loader) {
        super(loader, "http://www.gnu.org/licenses/gpl.html");
    }
}

package com.googlecode.fspotcloud.client.enduseraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;

public class ProjectSiteHandler extends LoadNewLocationAction {

	@Inject
	private ProjectSiteHandler(OpenNewTab loader) {
		super(loader, "http://code.google.com/p/fspotcloud");
	}
}

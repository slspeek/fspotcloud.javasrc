package com.googlecode.fspotcloud.client.enduseraction.about;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class AboutActions {

	private final Resources RESOURCES;

	public final ActionUIDef license;

	public final ActionUIDef project_hosting;

	public final ActionUIDef f_spot;

	public final ActionUIDef proton;

	public final ActionUIDef build_server;

	@Inject
	public AboutActions(Resources RESOURCES) {
		this.RESOURCES = RESOURCES;
		license = new ActionUIDef("license", "License", "View the license",
				RESOURCES.licenceIcon());
		project_hosting = new ActionUIDef("project-hosting", "Project site",
				"Go to the site on Google Project Hosting",
				RESOURCES.projectSiteIcon());
		f_spot = new ActionUIDef("f-spot", "F-Spot", "Go to the F-Spot site",
				RESOURCES.fspotIcon());
		proton = new ActionUIDef("proton", "Proton radio",
				"Go to the Proton Radio site", RESOURCES.protonIcon());
		build_server = new ActionUIDef("build-server", "Build server",
				"Go to the Jenkins build server", RESOURCES.buildServerIcon());
	}

}

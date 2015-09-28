package com.googlecode.fspotcloud.client.enduseraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;

public class BuildServerHandler extends LoadNewLocationAction {

	@Inject
	private BuildServerHandler(OpenNewTab loader) {
		super(loader, "http://188.200.115.158:9000/");
	}
}

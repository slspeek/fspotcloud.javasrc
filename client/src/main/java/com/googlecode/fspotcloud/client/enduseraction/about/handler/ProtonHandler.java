package com.googlecode.fspotcloud.client.enduseraction.about.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.LoadNewLocationAction;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;

public class ProtonHandler extends LoadNewLocationAction {

	@Inject
	private ProtonHandler(OpenNewTab loader) {
		super(loader, "http://www.protonradio.com/player/live/player.php");
	}
}

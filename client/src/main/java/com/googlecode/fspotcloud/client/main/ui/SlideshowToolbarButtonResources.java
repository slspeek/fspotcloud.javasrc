package com.googlecode.fspotcloud.client.main.ui;

import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButtonResources;

public interface SlideshowToolbarButtonResources extends ActionButtonResources {

	@Source("slideshowtoolbarbutton.css")
	AdminButtonStyle style();

	interface AdminButtonStyle extends Style { // inner-interface, just for fun
		// don't need to declare each String method
	}
}

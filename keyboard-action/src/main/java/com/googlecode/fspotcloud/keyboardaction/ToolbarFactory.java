package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbarResources;

public interface ToolbarFactory {

	ActionToolbar get(ActionToolbarResources actionToolbarResources);
}

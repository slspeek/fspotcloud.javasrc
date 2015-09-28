package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

public class ModeControllerProvider implements Provider<IModeController> {

	@Inject
	private EventBus eventBus;

	@Inject
	private ModeController modeController;

	@Override
	public IModeController get() {
		eventBus.addHandler(PlaceChangeEvent.TYPE, modeController);
		return modeController;
	}
}

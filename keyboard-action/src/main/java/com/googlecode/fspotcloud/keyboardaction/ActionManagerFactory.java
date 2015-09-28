package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;

public class ActionManagerFactory implements Provider<IActionManager> {

	private final EventBus eventBus;
	private final ActionManager actionManager;

	@Inject
	private ActionManagerFactory(EventBus eventBus, ActionManager actionManager) {
		this.eventBus = eventBus;
		this.actionManager = actionManager;
	}

	public IActionManager get() {
		eventBus.addHandler(KeyboardActionEvent.TYPE, actionManager);
		return actionManager;
	}
}

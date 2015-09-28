package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButtonResources;
import com.googlecode.fspotcloud.testharness.MainBuilder;

public class ActionButtonGwtTest extends GWTTestCase {

	private ActionButton button;
	private EventBus eventBus;
	private ActionUIDef okDef;

	@Override
	protected void gwtSetUp() throws Exception {
		eventBus = new SimpleEventBus();
		okDef = MainBuilder.LOGIN_DEF;
		final ActionButtonResources keyboardActionResources = GWT
				.create(ActionButtonResources.class);
		button = new ActionButton(okDef, eventBus, keyboardActionResources);
		super.gwtSetUp();
	}

	public void testSetCaption() throws Exception {
		assertEquals(button.getText(), okDef.getName());
	}

	public void testSetTooltip() throws Exception {
		assertEquals(button.asWidget().getTitle(), okDef.getDescription());
	}

	public void testOnEventNotForUs() throws Exception {
		ActionStateEvent event = new ActionStateEvent("", false);
		eventBus.fireEvent(event);
		assertTrue(button.isEnabled());
	}

	public void testOnEventForUs() throws Exception {
		ActionStateEvent event = new ActionStateEvent(okDef.getId(), false);
		eventBus.fireEvent(event);
		assertFalse(button.isEnabled());
	}

	@Override
	public String getModuleName() {
		return "com.googlecode.fspotcloud.KeyboardAction";
	}
}

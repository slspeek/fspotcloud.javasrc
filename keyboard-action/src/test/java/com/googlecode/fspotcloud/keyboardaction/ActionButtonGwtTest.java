package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.googlecode.fspotcloud.testharness.MainFactory;

public class ActionButtonGwtTest extends GWTTestCase {

    private ActionButton button;
    private EventBus eventBus;
    private ActionDef okDef;

    @Override
    protected void gwtSetUp() throws Exception {
        eventBus = new SimpleEventBus();
        okDef = MainFactory.OK_DEF;
        button = new ActionButton(okDef, eventBus);
        super.gwtSetUp();
    }


    public void testSetCaption() throws Exception {
        assertEquals(button.getText(), okDef.getName());
    }

    public void testSetTooltip() throws Exception {
        assertEquals(button.asWidget().getTitle(), okDef.getDescription());
    }


    public void testOnEventNotForUs() throws Exception {
        ActionEnableEvent event = new ActionEnableEvent("", false);
        eventBus.fireEvent(event);
        assertTrue(button.isEnabled());
    }

    public void testOnEventForUs() throws Exception {
        ActionEnableEvent event = new ActionEnableEvent(okDef.getId(), false);
        eventBus.fireEvent(event);
        assertFalse(button.isEnabled());
    }

    @Override
    public String getModuleName() {
        return "com.googlecode.fspotcloud.KeyboardAction";
    }
}

package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import java.util.logging.Level;
import java.util.logging.Logger;

class NativePreviewHandler implements Event.NativePreviewHandler {

    private final Logger log = Logger.getLogger(NativePreviewHandler.class.getName());
    private final EventBus eventBus;
    private final KeyboardPreferences keyboardPreferences;
    private final IModeController modeController;

    @Inject
    public NativePreviewHandler(EventBus eventBus, KeyboardPreferences keyboardPreferences, IModeController modeController) {
        this.eventBus = eventBus;
        this.keyboardPreferences = keyboardPreferences;
        this.modeController = modeController;
    }

    public void init() {
        Event.addNativePreviewHandler(this);
    }


    public void onPreviewNativeEvent(Event.NativePreviewEvent preview) {
        NativeEvent event = preview.getNativeEvent();
        int keycode = event.getKeyCode();

        if (!event.getType().equalsIgnoreCase("keydown") ||
                event.getAltKey() ||
                event.getCtrlKey() ||
                event.getMetaKey()
                ) {
            return;
        }

        log.log(Level.FINEST, "Event preview in keypress code: " + keycode);
        final String mode = modeController.getMode();
        final boolean shiftKey = event.getShiftKey();
        String actionId = keyboardPreferences.get(mode, new KeyStroke(shiftKey, keycode));

        if (actionId != null) {
            log.log(Level.FINEST, "ActionId found proceeding for: " + actionId);
            eventBus.fireEvent(new KeyboardActionEvent(actionId));
        }

    }
}

package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Event;
import com.google.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class NativePreviewHandler implements Event.NativePreviewHandler {

    private final Logger log = Logger.getLogger(NativePreviewHandler.class.getName());
    private final EventBus eventBus;
    private final KeyboardPreferences keyboardPreferences;
    private final IModeController modeController;

    @Inject
    public NativePreviewHandler(EventBus eventBus,
                                KeyboardPreferences keyboardPreferences,
                                IModeController modeController) {
        this.eventBus = eventBus;
        this.keyboardPreferences = keyboardPreferences;
        this.modeController = modeController;
        init();
    }

    private void init() {
        Event.addNativePreviewHandler(this);
    }


    public void onPreviewNativeEvent(final Event.NativePreviewEvent preview) {
        final NativeEvent event = preview.getNativeEvent();
        int keycode = event.getKeyCode();

        if (!event.getType().equalsIgnoreCase("keydown")

                ) {
            return;
        }

        final Modifiers modifiers = getModifiers(event);
        log.log(Level.FINEST, "Event preview in keypress code: " + keycode + " mods: " + modifiers);
        final String mode = modeController.getMode();
        String actionId = keyboardPreferences.get(mode, new KeyStroke(modifiers, keycode));

        if (actionId != null) {
            log.log(Level.FINEST, "ActionId found proceeding for: " + actionId);
            eventBus.fireEvent(new KeyboardActionEvent(actionId));
            preview.cancel();
        }
    }

    private Modifiers getModifiers(NativeEvent event) {
        final boolean shiftKey = event.getShiftKey();
        final boolean ctrlKey = event.getCtrlKey();
        final boolean altKey = event.getAltKey();
        Modifiers modifiers = new Modifiers(shiftKey, ctrlKey, altKey);
        return modifiers;
    }
}

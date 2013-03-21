package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Event;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.fspotcloud.keyboardaction.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class NativePreviewHandler implements Event.NativePreviewHandler {

    private final Logger log = Logger.getLogger(NativePreviewHandler.class.getName());
    private final EventBus eventBus;
    private final KeyboardPreferences keyboardPreferences;
    private final Provider<PlaceContext> placeContextProvider;


    @Inject
    private NativePreviewHandler(EventBus eventBus,
                                 KeyboardPreferences keyboardPreferences,
                                 Provider<PlaceContext> placeContextProvider) {
        this.eventBus = eventBus;
        this.keyboardPreferences = keyboardPreferences;
        this.placeContextProvider = placeContextProvider;
        init();
    }

    private void init() {
        Event.addNativePreviewHandler(this);
    }


    public void onPreviewNativeEvent(final Event.NativePreviewEvent preview) {
        final NativeEvent event = preview.getNativeEvent();
        int keycode = event.getKeyCode();

        if (!event.getType().equalsIgnoreCase("keydown")) {
            return;
        }
        final Modifiers modifiers = getModifiers(event);

        log.log(Level.FINEST, "Event preview in keydown-event  " + (new KeyStroke(modifiers, keycode)).toString() + " keycode: " + keycode);

        final PlaceContext placeContext = placeContextProvider.get();
        List<String> actionIdList = keyboardPreferences.get(placeContext, new KeyStroke(modifiers, keycode));
        if (!actionIdList.isEmpty()) {
            preview.cancel();
        }
        for (String actionId : actionIdList) {
            log.log(Level.FINEST, "ActionId found proceeding for: " + actionId);
            eventBus.fireEvent(new KeyboardActionEvent(actionId));
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

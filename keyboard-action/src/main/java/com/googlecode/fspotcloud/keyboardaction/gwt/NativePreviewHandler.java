package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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
    private final KeyPressMessagePopup keyPressPopup;
    private    final TimerInterface timerInterface;
    private final ActionUIRegistry actionUIRegistry;


    @Inject
    private NativePreviewHandler(EventBus eventBus,
                                 KeyboardPreferences keyboardPreferences,
                                 Provider<PlaceContext> placeContextProvider,
                                 KeyPressMessagePopup keyPressPopup,
                                 @PreviewTimer TimerInterface timerInterface,
                                 ActionUIRegistry actionUIRegistry) {
        this.eventBus = eventBus;
        this.keyboardPreferences = keyboardPreferences;
        this.placeContextProvider = placeContextProvider;
        this.keyPressPopup = keyPressPopup;
        this.timerInterface = timerInterface;
        this.actionUIRegistry = actionUIRegistry;
        init();
    }

    private void init() {
        Event.addNativePreviewHandler(this);
    }


    public void onPreviewNativeEvent(final Event.NativePreviewEvent preview) {
        final NativeEvent event = preview.getNativeEvent();
        int keycode = event.getKeyCode();

        if (!event.getType().equalsIgnoreCase("keydown")
                || keycode == KeyCodes.KEY_ALT
                || keycode == KeyCodes.KEY_SHIFT
                || keycode == KeyCodes.KEY_CTRL) {
            return;
        }
        final Modifiers modifiers = getModifiers(event);
        final String keyString = (new KeyStroke(modifiers, keycode)).toString();
        final String pressed = keyString + " pressed.";
        keyPressPopup.setSafeHtml(new SafeHtmlBuilder().appendEscaped(pressed).toSafeHtml());
        keyPressPopup.center();
        keyPressPopup.setPopupPosition(keyPressPopup.getAbsoluteLeft(), 3);
        keyPressPopup.show();

        log.log(Level.FINEST, "Event preview in keydown-event  " + keyString + " keycode: " + keycode);

        final PlaceContext placeContext = placeContextProvider.get();
        List<String> actionIdList = keyboardPreferences.get(placeContext, new KeyStroke(modifiers, keycode));
        if (!actionIdList.isEmpty()) {
            preview.cancel();
        } else {
            keyPressPopup.setNotFound(true);
        }

        for (final String actionId : actionIdList) {
            log.log(Level.FINEST, "ActionId found proceeding for: " + actionId);
            keyPressPopup.setSafeHtml(new SafeHtmlBuilder().appendEscaped(
                    pressed + ", processing action: " + actionUIRegistry.getAction(actionId).getName()
            ).toSafeHtml());
            eventBus.fireEvent(new KeyboardActionEvent(actionId));
        }
        timerInterface.setRunnable(new Runnable() {
            @Override
            public void run() {
                keyPressPopup.hide();
                keyPressPopup.setNotFound(false);
            }
        });
        timerInterface.schedule(400);
    }

    private Modifiers getModifiers(NativeEvent event) {
        final boolean shiftKey = event.getShiftKey();
        final boolean ctrlKey = event.getCtrlKey();
        final boolean altKey = event.getAltKey();
        Modifiers modifiers = new Modifiers(shiftKey, ctrlKey, altKey);
        return modifiers;
    }
}

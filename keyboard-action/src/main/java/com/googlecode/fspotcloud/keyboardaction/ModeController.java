package com.googlecode.fspotcloud.keyboardaction;


import com.google.common.base.Objects;
import com.google.web.bindery.event.shared.EventBus;

import java.util.logging.Level;
import java.util.logging.Logger;

class ModeController implements IModeController {

    private final Logger log = Logger.getLogger(ModeController.class.getName());
    private final String defaultMode;
    private String mode;
    private final KeyboardPreferences keyboardPreferences;
    private final EventBus eventBus;

    public ModeController(String defaultMode, KeyboardPreferences keyboardPreferences, EventBus eventBus) {
        this.eventBus = eventBus;
        this.keyboardPreferences = keyboardPreferences;
        this.defaultMode = defaultMode;
    }

    @Override
    public void initButtonEnableStates() {
        setMode(defaultMode);
    }

    @Override
    public void setMode(String mode) {
        if (!Objects.equal(this.mode, mode)) {
            this.mode = mode;
            log.log(Level.FINEST, "Set mode to: " + mode);
            fireEnabledStateEvens();
        }
    }

    @Override
    public String getMode() {
        return mode;
    }

    private void fireEnabledStateEvens() {
        for (String actionId : keyboardPreferences.allActions()) {
            boolean relevant = keyboardPreferences.isRelevant(actionId, mode);
            ActionEnableEvent event = new ActionEnableEvent(actionId, relevant);
            log.log(Level.FINEST, "Firing: " + event);
            eventBus.fireEvent(event);
        }
    }

}

package com.googlecode.fspotcloud.keyboardaction;


import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeController implements IModeController {

    private final Logger log = Logger.getLogger(ModeController.class.getName());
    private final String defaultMode;
    private String mode;
    private final KeyboardPreferences keyboardPreferences;
    private final EventBus eventBus;

    @Inject
    private ModeController(ModesProvider modesProvider, KeyboardPreferences keyboardPreferences, EventBus eventBus) {
        this.eventBus = eventBus;
        this.keyboardPreferences = keyboardPreferences;
        this.defaultMode = modesProvider.getModes()[0];
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
            ActionStateEvent event;
            if (relevant) {
                String acceleratorString;
                StringBuffer sb = new StringBuffer();
                KeyStroke[] keys = keyboardPreferences.getKeysForAction(mode, actionId);
                acceleratorString = Joiner.on(" or ").join(keys);
                event = new ActionStateEvent(actionId, relevant, acceleratorString);
            } else {
                event = new ActionStateEvent(actionId, relevant);
            }
            log.log(Level.FINEST, "Firing: " + event);
            eventBus.fireEvent(event);
        }
    }

}

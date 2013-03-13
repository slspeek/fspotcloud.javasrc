package com.googlecode.fspotcloud.keyboardaction;


import com.google.common.base.Joiner;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Sets.newHashSet;

public class ModeController implements IModeController {

    private final Logger log = Logger.getLogger(ModeController.class.getName());
    private final KeyboardPreferences keyboardPreferences;
    private final Set<String> flags = newHashSet();
    private final IPlaceController placeController;
    private final WidgetRegistry widgetRegistry;

    @Inject
    private ModeController(
            KeyboardPreferences keyboardPreferences,
            IPlaceController placeController,
            WidgetRegistry widgetRegistry) {
        this.keyboardPreferences = keyboardPreferences;
        this.placeController = placeController;
        this.widgetRegistry = widgetRegistry;
    }

    @Override
    public void initButtonEnableStates() {
        fireEnabledStateEvens();
    }

    @Override
    public Set<String> getFlags() {
        return flags;
    }

    @Override
    public void setFlag(String flag) {
        flags.add(flag);
        fireEnabledStateEvens();
    }

    @Override
    public void unsetFlag(String flag) {
        flags.remove(flag);
        fireEnabledStateEvens();
    }

    @Override
    public void clearFlags() {
        flags.clear();
    }

    private void fireEnabledStateEvens() {
        PlaceContext placeContext = new PlaceContext(placeController.getWhere().getClass(), getFlags());
        for (String actionId : keyboardPreferences.allActions()) {
            boolean relevant = keyboardPreferences.isRelevant(actionId, placeContext);
            ActionStateEvent event;
            if (relevant) {
                String acceleratorString;
                StringBuffer sb = new StringBuffer();
                List<KeyStroke> keys = keyboardPreferences.getKeysForAction(placeContext, actionId);
                acceleratorString = Joiner.on(" or ").join(keys);
                event = new ActionStateEvent(actionId, relevant, acceleratorString);
            } else {
                event = new ActionStateEvent(actionId, relevant);
            }
            log.log(Level.FINEST, "Firing: " + event);
            Set<ActionWidget> widgetSet = widgetRegistry.get(actionId);
            for (ActionWidget widget : widgetSet) {
                widget.onEvent(event);
            }
        }
    }

    @Override
    public void onPlaceChange(PlaceChangeEvent event) {
        fireEnabledStateEvens();
    }
}

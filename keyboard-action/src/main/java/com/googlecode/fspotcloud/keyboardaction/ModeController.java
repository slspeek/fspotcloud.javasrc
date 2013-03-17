package com.googlecode.fspotcloud.keyboardaction;


import com.google.common.base.Joiner;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceChangeEvent;
import com.google.inject.Inject;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Sets.newEnumSet;
import static com.google.common.collect.Sets.newHashSet;

public class ModeController implements IModeController {

    private final Logger log = Logger.getLogger(ModeController.class.getName());
    private final KeyboardPreferences keyboardPreferences;
    private final Set<String> flags = newHashSet();
    private final IPlaceController placeController;
    private final WidgetRegistry widgetRegistry;
    private PlaceContext placeContext = new PlaceContext(Place.NOWHERE.getClass());

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
    public void setFlag(String name, boolean set) {
        if(set) {
            setFlag(name);
        }  else {
            unsetFlag(name);
        }
    }

    @Override
    public Set<String> getFlags() {
        return newHashSet(flags);
    }

    @Override
    public void setFlag(String flag) {
        log.log(Level.FINER, "set flag: " + flag);
        final boolean add = flags.add(flag);
        if (add) {
        }
            fireEnabledStateEvens();
    }

    @Override
    public void unsetFlag(String flag) {
        log.log(Level.FINER, "unset flag: " + flag);
        final boolean remove = flags.remove(flag);
        if (remove) {
        }
            fireEnabledStateEvens();
    }

    @Override
    public void clearFlags() {
        flags.clear();
        fireEnabledStateEvens();
    }

    private void fireEnabledStateEvens() {
        PlaceContext newPlaceContext = new PlaceContext(placeController.getWhere().getClass(), getFlags());
        log.log(Level.FINEST, "firingEnabledStateEvents: old: " + placeContext + " new: " +newPlaceContext );
        if (!this.placeContext.equals(newPlaceContext)) {
            this.placeContext = newPlaceContext;
            log.log(Level.FINER, "New place context " + newPlaceContext + " All action buttons we be notified.");
            for (String actionId : keyboardPreferences.allActions()) {
                boolean relevant = keyboardPreferences.isRelevant(actionId, newPlaceContext);
                ActionStateEvent event;
                if (relevant) {
                    String acceleratorString;
                    StringBuffer sb = new StringBuffer();
                    List<KeyStroke> keys = keyboardPreferences.getKeysForAction(newPlaceContext, actionId);
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
    }

    @Override
    public void onPlaceChange(PlaceChangeEvent event) {
        fireEnabledStateEvens();
    }
}

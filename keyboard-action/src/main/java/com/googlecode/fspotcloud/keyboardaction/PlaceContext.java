package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;

import java.util.Set;

public class PlaceContext {

    private final Class<? extends Place> place;
    private final Set<String> flags;

    PlaceContext(Class<? extends Place> place, Set<String> flags) {
        this.place = place;
        this.flags = flags;
    }

    public Class<? extends Place> getPlace() {
        return place;
    }

    public Set<String> getFlags() {
        return flags;
    }
}

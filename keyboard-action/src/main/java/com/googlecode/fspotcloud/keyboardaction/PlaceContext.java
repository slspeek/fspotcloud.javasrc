package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;

import java.util.Set;

public class PlaceContext {

    private final Place place;
    private final Set<String> flags;

    public PlaceContext(Place place, Set<String> flags) {
        this.place = place;
        this.flags = flags;
    }

    public Place getPlace() {
        return place;
    }

    public Set<String> getFlags() {
        return flags;
    }
}

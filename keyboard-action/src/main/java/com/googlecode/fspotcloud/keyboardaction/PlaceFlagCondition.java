package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;

public class PlaceFlagCondition {

    private final Place place;
    private final FlagCondition condition;

    public PlaceFlagCondition(Place place, FlagCondition condition) {
        this.place = place;
        this.condition = condition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceFlagCondition that = (PlaceFlagCondition) o;

        if (!condition.equals(that.condition)) return false;
        if (!place.equals(that.place)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = place.hashCode();
        result = 31 * result + condition.hashCode();
        return result;
    }
}

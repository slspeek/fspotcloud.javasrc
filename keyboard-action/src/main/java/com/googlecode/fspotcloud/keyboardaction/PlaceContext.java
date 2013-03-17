package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.base.Objects;
import com.google.gwt.place.shared.Place;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class PlaceContext {

    private static final Set<String> emptySet = newHashSet();
    private final Class<? extends Place> place;
    private final Set<String> flags;

    PlaceContext(Class<? extends Place> place, Set<String> flags) {
        this.place = place;
        this.flags = flags;
    }

    PlaceContext(Class<? extends Place> place) {
        this(place, emptySet);
    }

    public Class<? extends Place> getPlace() {
        return place;
    }

    public Set<String> getFlags() {
        return flags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaceContext)) return false;

        PlaceContext that = (PlaceContext) o;

        if (!flags.equals(that.flags)) return false;
        if (!place.equals(that.place)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(place, flags);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("place-class", place)
                .add("flags", flags).toString();
    }
}

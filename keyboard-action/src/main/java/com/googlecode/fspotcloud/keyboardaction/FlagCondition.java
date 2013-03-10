package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;

import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class FlagCondition {

    public static final FlagCondition EMPTY = new FlagCondition();
    private final Set<String> excludedFlags;
    private final Set<String> necessaryFlags;



    public FlagCondition() {
        this.excludedFlags = newHashSet();
        this.necessaryFlags = newHashSet();
    }

    public void exclude(String ... flags) {
        excludedFlags.addAll(newArrayList(flags));
    }

    public void needs(String... flags) {
        necessaryFlags.addAll(newArrayList(flags));
    }

    public boolean holds(Set<String> flags) {
        boolean result = true;
        for (String necessaryFlag: necessaryFlags) {
            if (!flags.contains(necessaryFlag)) {
                result = false;
            }
        }
        for (String excluded: excludedFlags) {
            if(flags.contains(excluded)) {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlagCondition that = (FlagCondition) o;

        if (!excludedFlags.equals(that.excludedFlags)) return false;
        if (!necessaryFlags.equals(that.necessaryFlags)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = excludedFlags.hashCode();
        result = 31 * result + necessaryFlags.hashCode();
        return result;
    }
}

package com.googlecode.fspotcloud.keyboardaction;

import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

public class FlagsRule {

    public static final FlagsRule EMPTY = new FlagsRule();
    private final Set<String> excludedFlags;
    private final Set<String> necessaryFlags;

    public static FlagsRule needing(String ... flags) {
        FlagsRule rule = new FlagsRule();
        rule.needs(flags);
        return rule;
    }

    public static FlagsRule excluding(String... flags) {
        FlagsRule rule = new FlagsRule();
        rule.excludes(flags);
        return rule;
    }

    public FlagsRule(FlagsRule old) {
        excludedFlags = newHashSet(old.excludedFlags);
        necessaryFlags = newHashSet(old.necessaryFlags);
    }

    public FlagsRule() {
        this.excludedFlags = newHashSet();
        this.necessaryFlags = newHashSet();
    }

    public FlagsRule excludes(String... flags) {
        excludedFlags.addAll(newArrayList(flags));
        return this;
    }

    public FlagsRule needs(String... flags) {
        necessaryFlags.addAll(newArrayList(flags));
        return this;
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

        FlagsRule that = (FlagsRule) o;

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

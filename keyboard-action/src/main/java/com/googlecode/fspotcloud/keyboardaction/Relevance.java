package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.gwt.place.shared.Place;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@GwtCompatible
public class Relevance {

    private final Logger log = Logger.getLogger(Relevance.class.getName());
    private final List<KeyStroke> defaultKeys = newArrayList();
    private final Map<Class<? extends Place>, Set<FlagsRule>> placeConditions = newHashMap();
    private final Map<PlaceFlagCondition, KeyStroke[]> overridesMap = newHashMap();
    private final List<Class<? extends Place>> defaultPlaces = newArrayList();
    private final FlagsRule defaultRule;

    public Relevance(FlagsRule defaultRule, Class<? extends Place>... defaultPlaces) {
        this.defaultRule = defaultRule;
        for (Class<? extends Place> place : defaultPlaces) {
            this.defaultPlaces.add(place);
        }
    }

    public Relevance(Class<? extends Place>... defaultPlaces) {
        this.defaultRule = FlagsRule.EMPTY;
        for (Class<? extends Place> place : defaultPlaces) {
            this.defaultPlaces.add(place);
        }
    }

    public Relevance addDefaultKeys(KeyStroke... keyStroke) {
        for (KeyStroke key : keyStroke) {
            defaultKeys.add(key);
        }
        return this;
    }

    public Relevance addRule(Class<? extends Place> place, FlagsRule condition, KeyStroke... keyStrokes) {
        addPlaceCondition(place, condition);
        PlaceFlagCondition placeFlagCondition = new PlaceFlagCondition(place, condition);
        overridesMap.put(placeFlagCondition, keyStrokes);
        return this;
    }

    public Relevance addRule(List<Class<? extends Place>> placeList, FlagsRule condition, KeyStroke... keyStrokes) {
        for (Class<? extends Place> place : placeList) {
            addRule(place, condition, keyStrokes);
        }
        return this;
    }

    public Relevance addRule(Class<? extends Place> place, KeyStroke... keyStrokes) {
        return addRule(place, FlagsRule.EMPTY, keyStrokes);
    }

    public List<Class<? extends Place>> getDefaultPlaces() {
        return defaultPlaces;
    }

    public List<KeyStroke> getKeys(PlaceContext placeContext) {
        List<KeyStroke> result = newArrayList();
        final Class<? extends Place> place = placeContext.getPlace();
        final Set<String> flags = placeContext.getFlags();
        if ((defaultPlaces.isEmpty() || defaultPlaces.contains(place)) && defaultRule.holds(flags)) {
            result = defaultKeys;
        }
        List<KeyStroke> overrides = newArrayList();
        final Set<FlagsRule> conditionSet = placeConditions.get(place);
        if (conditionSet != null) {
            for (FlagsRule condition : conditionSet) {
                if (condition.holds(flags)) {
                    overrides.addAll(getKeyStrokes(place, condition));
                }
            }
        }
        if (!overrides.isEmpty()) {
            result = overrides;
        }
        //log.log(Level.SEVERE, "getKeys for: " + placeContext + " returns: " + result);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("defaultKeys",
                newArrayList(defaultKeys)).add("defaultPlaces", defaultPlaces)
                .add("overrides", overridesMap).toString();
    }

    public List<KeyStroke> getDefaultKeys() {
        return defaultKeys;
    }

    private void addPlaceCondition(Class<? extends Place> placeClasses, FlagsRule condition) {
        Set<FlagsRule> conditionSet = placeConditions.get(placeClasses);
        if (conditionSet == null) {
            conditionSet = newHashSet();
        }
        conditionSet.add(condition);
        placeConditions.put(placeClasses, conditionSet);
    }

    private List<KeyStroke> getKeyStrokes(Class<? extends Place> placeClasses, FlagsRule condition) {
        final KeyStroke[] elements = overridesMap.get(new PlaceFlagCondition(placeClasses, condition));
        if (elements != null) {
            return newArrayList(elements);

        } else {
            return newArrayList();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Relevance relevance = (Relevance) o;

        if (!defaultKeys.equals(relevance.defaultKeys)) return false;
        if (!defaultPlaces.equals(relevance.defaultPlaces)) return false;
        if (!overridesMap.equals(relevance.overridesMap)) return false;
        if (!placeConditions.equals(relevance.placeConditions)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = defaultKeys.hashCode();
        result = 31 * result + placeConditions.hashCode();
        result = 31 * result + overridesMap.hashCode();
        result = 31 * result + defaultPlaces.hashCode();
        return result;
    }

    public void addRule(List<Class<? extends Place>> placeClasses, KeyStroke stroke) {
        for (Class<? extends Place> place: placeClasses) {
            addRule(place, stroke);
        }
    }
}

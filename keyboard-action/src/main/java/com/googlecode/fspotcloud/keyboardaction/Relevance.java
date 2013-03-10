package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.gwt.place.shared.Place;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@GwtCompatible
public class Relevance {

    private final List<KeyStroke> defaultKeys;
    private final Map<Place, Set<FlagCondition>> placeConditions = newHashMap();
    private final Map<PlaceFlagCondition, KeyStroke[]> overridesMap = newHashMap();
    private final List<Place> defaultPlaces;

    public Relevance(List<KeyStroke> defaultKeys, List<Place> defaultPlaces) {
        this.defaultPlaces = defaultPlaces;
        this.defaultKeys = defaultKeys;
    }

    public Relevance override(Place place, FlagCondition condition, KeyStroke... keyStrokes) {
        addPlaceCondition(place, condition);
        PlaceFlagCondition placeFlagCondition = new PlaceFlagCondition(place, condition);
        overridesMap.put(placeFlagCondition, keyStrokes);
        return this;
    }

    public Relevance override(Place place, KeyStroke... keyStrokes) {
        return override(place, FlagCondition.EMPTY, keyStrokes);
    }

    public List<KeyStroke> getKeys(PlaceContext placeContext) {
        List<KeyStroke> result = newArrayList();
        final Place place = placeContext.getPlace();
        if (defaultPlaces.contains(place)) {
            result = defaultKeys;
        }
        List<KeyStroke> overrides = newArrayList();
        for (FlagCondition condition : placeConditions.get(place)) {
            if (condition.holds(placeContext.getFlags())) {
                overrides.addAll(getKeyStrokes(place, condition));
            }
        }
        if (!overrides.isEmpty()) {
            result = overrides;
        }
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

    private void addPlaceCondition(Place place, FlagCondition condition) {
        Set<FlagCondition> conditionSet = placeConditions.get(place);
        if (conditionSet == null) {
            conditionSet = newHashSet();
        }
        conditionSet.add(condition);
    }

    private List<KeyStroke> getKeyStrokes(Place place, FlagCondition condition) {
        final KeyStroke[] elements = overridesMap.get(new PlaceFlagCondition(place, condition));
        if (elements != null) {
            return newArrayList(elements);

        } else {
            return newArrayList();
        }
    }


}

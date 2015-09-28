package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.gwt.place.shared.Place;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
//import java.util.logging.Logger;

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@GwtCompatible
public class Relevance {

	//private final Logger log = Logger.getLogger(Relevance.class.getName());
	private final List<KeyStroke> defaultKeys = newArrayList();
	private final Map<Class<? extends Place>, Set<FlagsRule>> placeConditions = newHashMap();
	private final Map<PlaceFlagCondition, List<KeyStroke>> overridesMap = newHashMap();
	private final List<Class<? extends Place>> defaultPlaces = newArrayList();
	private final FlagsRule defaultRule;

	public Relevance(FlagsRule defaultRule,
			Class<? extends Place>... defaultPlaces) {
		this.defaultRule = defaultRule;
		Collections.addAll(this.defaultPlaces, defaultPlaces);
	}

	public Relevance(Class<? extends Place>... defaultPlaces) {
		this.defaultRule = FlagsRule.EMPTY;
		Collections.addAll(this.defaultPlaces, defaultPlaces);
	}

	public Relevance addDefaultKeys(KeyStroke... keyStroke) {
		Collections.addAll(defaultKeys, keyStroke);
		return this;
	}

	public Relevance addRule(Class<? extends Place> place, FlagsRule condition,
			KeyStroke... keyStrokes) {
		addPlaceCondition(place, condition);
		PlaceFlagCondition placeFlagCondition = new PlaceFlagCondition(place,
				condition);
		List<KeyStroke> keysList = newArrayList(keyStrokes);
		overridesMap.put(placeFlagCondition, keysList);
		return this;
	}

	public Relevance addRule(List<Class<? extends Place>> placeList,
			FlagsRule condition, KeyStroke... keyStrokes) {
		for (Class<? extends Place> place : placeList) {
			addRule(place, condition, keyStrokes);
		}
		return this;
	}

	public Relevance addRule(Class<? extends Place> place,
			KeyStroke... keyStrokes) {
		return addRule(place, FlagsRule.EMPTY, keyStrokes);
	}

	public List<Class<? extends Place>> getDefaultPlaces() {
		return defaultPlaces;
	}

	public List<KeyStroke> getKeys(PlaceContext placeContext) {
		List<KeyStroke> result = newArrayList();
		final Class<? extends Place> place = placeContext.getPlace();
		final Set<String> flags = placeContext.getFlags();
		if (defaultPlaces.isEmpty() || (defaultPlaces.contains(place))
				&& defaultRule.holds(flags)) {
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
		return result;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("defaultKeys", defaultKeys)
				.add("defaultRule", defaultRule)
				.add("defaultPlaces", defaultPlaces)
				.add("overrides", overridesMap).toString();
	}

	public List<KeyStroke> getDefaultKeys() {
		return defaultKeys;
	}

	private void addPlaceCondition(Class<? extends Place> placeClasses,
			FlagsRule condition) {
		Set<FlagsRule> conditionSet = placeConditions.get(placeClasses);
		if (conditionSet == null) {
			conditionSet = newHashSet();
		}
		conditionSet.add(condition);
		placeConditions.put(placeClasses, conditionSet);
	}

	private List<KeyStroke> getKeyStrokes(Class<? extends Place> placeClasses,
			FlagsRule condition) {
		final List<KeyStroke> elements = overridesMap
				.get(new PlaceFlagCondition(placeClasses, condition));
		if (elements != null) {
			return elements;
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Relevance) {
			Relevance relevance = (Relevance) o;
			return equal(defaultRule, relevance.defaultRule)
					&& equal(defaultKeys, relevance.defaultKeys)
					&& equal(defaultPlaces, relevance.defaultPlaces)
					&& equal(overridesMap, relevance.overridesMap)
					&& equal(placeConditions, relevance.placeConditions);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(defaultRule, defaultKeys, defaultPlaces,
				overridesMap, placeConditions);
	}

	public void addRule(List<Class<? extends Place>> placeClasses,
			KeyStroke stroke) {
		for (Class<? extends Place> place : placeClasses) {
			addRule(place, stroke);
		}
	}
}

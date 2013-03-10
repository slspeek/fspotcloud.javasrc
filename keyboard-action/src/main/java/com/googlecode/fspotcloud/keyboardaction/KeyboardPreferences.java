package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@GwtCompatible
public class KeyboardPreferences {

    private final Logger logger = Logger.getLogger(KeyboardPreferences.class.getName());
    private final Map<ActionKey, String> keyStringMap = newHashMap();
    private final Map<String, Relevance> bindingsMap = newHashMap();
    private final Map<Relevance, String> relevanceMap = newHashMap();
    @Inject
    KeyboardPreferences() {

    }

    void bind(String id, Relevance relevance) {
        relevanceMap.put(relevance, id);
        bindingsMap.put(id, relevance);
    }

    String get(PlaceContext placeContext, KeyStroke stroke) {
        for (Relevance relevance : relevanceMap.keySet()) {
            List<KeyStroke> list = relevance.getKeys(placeContext);
            if (list.contains(stroke)) {
                return relevanceMap.get(relevance);
            }
        }
        return null;
    }

    boolean isRelevant(String actionId, PlaceContext placeContext) {
        return !(bindingsMap.get(actionId).getKeys(placeContext).size() == 0);
    }

    List<KeyStroke> getKeysForAction(PlaceContext placeContext, String actionId) {
        return bindingsMap.get(actionId).getKeys(placeContext);
    }

    Set<String> allActions() {
        return bindingsMap.keySet();
    }

    public List<KeyStroke> getDefaultKeysForAction(String id) {
        return bindingsMap.get(id).getDefaultKeys();

    }

    Set<String> allRelevantActions(PlaceContext placeContext    ) {
        HashSet result = newHashSet();
        for (String actionId : allActions()) {
            if (isRelevant(actionId, placeContext)) {
                result.add(actionId);
            }
        }
        return result;
    }
}

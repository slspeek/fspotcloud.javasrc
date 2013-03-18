package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

@GwtCompatible
public class KeyboardPreferences {

    private final Logger log = Logger.getLogger(KeyboardPreferences.class.getName());
    private final Map<String, Relevance> bindingsMap = newHashMap();
    private final Map<Relevance, String> relevanceMap = newHashMap();
    @Inject
    KeyboardPreferences() {
    }

    void bind(String id, Relevance relevance) {
        relevanceMap.put(relevance, id);
        bindingsMap.put(id, relevance);
    }

    public List<String> get(PlaceContext placeContext, KeyStroke stroke) {
        List<String> result = newArrayList();
        for (Relevance relevance : relevanceMap.keySet()) {
            List<KeyStroke> list = relevance.getKeys(placeContext);
            if (list.contains(stroke)) {
                result.add(relevanceMap.get(relevance));
            }
        }
        return result;
    }

    boolean isRelevant(String actionId, PlaceContext placeContext) {
        return !(getKeysForAction(placeContext, actionId).size() == 0);
    }

    public List<KeyStroke> getKeysForAction(PlaceContext placeContext, String actionId) {
        List<KeyStroke> keyStrokes = newArrayList();
        final Relevance relevance = bindingsMap.get(actionId);
        if (relevance != null) {
            keyStrokes.addAll(relevance.getKeys(placeContext));
        }
        //log.log(Level.SEVERE, "processed " + actionId + " to " + keyStrokes);
        return keyStrokes;
    }

    public Set<String> allActions() {
        return bindingsMap.keySet();
    }

    public List<KeyStroke> getDefaultKeysForAction(String actionId) {
        List<KeyStroke> keyStrokes = newArrayList();
        final Relevance relevance = bindingsMap.get(actionId);
        if (relevance != null) {
            keyStrokes.addAll(relevance.getDefaultKeys());
        }
        return keyStrokes;
    }

    public Set<String> allRelevantActions(PlaceContext placeContext) {
        HashSet result = newHashSet();
        for (String actionId : allActions()) {
            if (isRelevant(actionId, placeContext)) {
                result.add(actionId);
            }
        }
        return result;
    }
}

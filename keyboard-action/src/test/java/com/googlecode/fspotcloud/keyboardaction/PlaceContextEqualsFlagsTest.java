package com.googlecode.fspotcloud.keyboardaction;


import com.google.gwt.place.shared.Place;
import com.googlecode.fspotcloud.test.EqualsTest;

import static com.google.common.collect.Sets.newHashSet;

public class PlaceContextEqualsFlagsTest extends EqualsTest<PlaceContext> {
    @Override
    protected PlaceContext getOne() {
        return new PlaceContext(Place.NOWHERE.getClass(), newHashSet("Foo"));
    }

       @Override
    protected PlaceContext getDifferentOne() {
        return new PlaceContext(Place.NOWHERE.getClass(), newHashSet("Bar"));
    }
}

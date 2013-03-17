package com.googlecode.fspotcloud.keyboardaction;


import com.google.gwt.place.shared.Place;
import com.googlecode.fspotcloud.test.EqualsTest;
import com.googlecode.fspotcloud.testharness.HomePlace;

public class PlaceContextEqualsPlaceTest extends EqualsTest<PlaceContext> {
    @Override
    protected PlaceContext getOne() {
        return new PlaceContext(Place.NOWHERE.getClass());
    }

       @Override
    protected PlaceContext getDifferentOne() {
        return new PlaceContext(HomePlace.class);
    }
}

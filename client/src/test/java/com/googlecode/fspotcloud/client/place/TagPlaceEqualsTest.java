package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;

public class TagPlaceEqualsTest extends EqualsTest<TagPlace> {
    @Override
    protected TagPlace getOne() {
        return new TagPlace("1");
    }

    @Override
    protected TagPlace getDifferentOne() {
        return new TagPlace("2");
    }
}

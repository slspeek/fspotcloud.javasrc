package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;

public class UserAccountPlaceTest extends EqualsTest<UserAccountPlace> {
    @Override
    protected UserAccountPlace getOne() {
        return new UserAccountPlace();
    }

    @Override
    protected UserAccountPlace getDifferentOne() {
        return null;
    }
}

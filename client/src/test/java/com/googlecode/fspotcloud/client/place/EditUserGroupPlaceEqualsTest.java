package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;

public class EditUserGroupPlaceEqualsTest extends EqualsTest<EditUserGroupPlace> {
    @Override
    protected EditUserGroupPlace getOne() {
        return new EditUserGroupPlace(1l);
    }

    @Override
    protected EditUserGroupPlace getTheOther() {
        return new EditUserGroupPlace(1l);
    }

    @Override
    protected EditUserGroupPlace getDifferentOne() {
        return new EditUserGroupPlace(2l);
    }
}

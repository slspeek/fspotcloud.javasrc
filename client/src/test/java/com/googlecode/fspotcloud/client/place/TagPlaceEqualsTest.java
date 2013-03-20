package com.googlecode.fspotcloud.client.place;

import com.googlecode.fspotcloud.test.EqualsTest;

public class TagPlaceEqualsTest extends EqualsTest<DashboardPlace> {
    @Override
    protected DashboardPlace getOne() {
        return new DashboardPlace("1");
    }

    @Override
    protected DashboardPlace getDifferentOne() {
        return new DashboardPlace("2");
    }
}

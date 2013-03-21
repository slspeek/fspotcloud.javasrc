package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;

public class ActionCategoryEqualsNameTest extends EqualsTest<ActionCategory> {
    @Override
    protected ActionCategory getOne() {
        final ActionCategory foo = new ActionCategory("Foo");
        return foo;
    }

    @Override
    protected ActionCategory getDifferentOne() {
        return new ActionCategory("Bar");
    }
}

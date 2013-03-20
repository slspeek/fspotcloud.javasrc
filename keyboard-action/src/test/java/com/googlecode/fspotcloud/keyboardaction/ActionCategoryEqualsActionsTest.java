package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;
import com.googlecode.fspotcloud.testharness.MainBuilder;

public class ActionCategoryEqualsActionsTest extends EqualsTest<ActionCategory> {
    @Override
    protected ActionCategory getOne() {
        final ActionCategory foo = new ActionCategory("Foo");
        foo.add(MainBuilder.DEMO_DEF);
        return foo;
    }

    @Override
    protected ActionCategory getDifferentOne() {
        return new ActionCategory("Foo");
    }
}

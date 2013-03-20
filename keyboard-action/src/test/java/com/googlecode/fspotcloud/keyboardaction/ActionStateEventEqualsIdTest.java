package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;

public class ActionStateEventEqualsIdTest extends EqualsTest<ActionStateEvent> {

    @Override
    protected ActionStateEvent getOne() {
        return new ActionStateEvent("1", true, "alt-4");
    }

    @Override
    protected ActionStateEvent getDifferentOne() {
        return new ActionStateEvent("2", true, "alt-4");
    }
}

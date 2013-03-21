package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;

public class ActionDemoEventEqualsActionIdTest extends EqualsTest<ActionDemoEvent> {

    @Override
    protected ActionDemoEvent getOne() {
        return new ActionDemoEvent("Foo", true);
    }

    @Override
    protected ActionDemoEvent getDifferentOne() {
        return new ActionDemoEvent("Bar", true);
    }
}

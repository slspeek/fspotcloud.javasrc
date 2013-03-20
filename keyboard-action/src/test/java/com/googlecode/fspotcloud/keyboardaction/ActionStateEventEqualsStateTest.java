package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionStateEventEqualsStateTest extends EqualsTest<ActionStateEvent> {

    @Override
    protected ActionStateEvent getOne() {
        return new ActionStateEvent("1", true, "alt-4");
    }

    @Override
    protected ActionStateEvent getDifferentOne() {
        return new ActionStateEvent("1", false, "alt-4");
    }
}

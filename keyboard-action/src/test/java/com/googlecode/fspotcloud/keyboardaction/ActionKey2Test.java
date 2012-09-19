package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;

public class ActionKey2Test extends EqualsTest<ActionKey> {
    @Override
    protected ActionKey getOne() {
        return new ActionKey(KeyboardPreferencesTest.MODE, KeyStrokeTest.KEY_STROKE_SHIFT_A);
    }

    @Override
    protected ActionKey getTheOther() {
        return new ActionKey(KeyboardPreferencesTest.MODE, KeyStrokeTest.KEY_STROKE_SHIFT_A);
    }

    @Override
    protected ActionKey getDifferentOne() {
        return new ActionKey(KeyboardPreferencesTest.MODE, new KeyStroke(false, 'a'));
    }
}

package com.googlecode.fspotcloud.keyboardaction;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

public class KeyStrokeTest  extends EqualsTest<KeyStroke> {

    public static final char KEY_CODE = 'a';
    public static final KeyStroke KEY_STROKE_SHIFT_A = new KeyStroke(Modifiers.SHIFT, KEY_CODE);

    @Override
    protected KeyStroke getOne() {
        return KEY_STROKE_SHIFT_A;
    }

    @Override
    protected KeyStroke getTheOther() {
        return new KeyStroke(Modifiers.SHIFT, KEY_CODE);
    }

    @Override
    protected KeyStroke getDifferentOne() {
        return new KeyStroke(Modifiers.NONE, KEY_CODE);
    }


}

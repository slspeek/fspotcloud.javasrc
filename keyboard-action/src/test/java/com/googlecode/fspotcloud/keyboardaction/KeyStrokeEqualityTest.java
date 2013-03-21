package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.test.EqualityTest;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.alt;
import static com.googlecode.fspotcloud.keyboardaction.KeyStroke.shift;

public class KeyStrokeEqualityTest extends EqualityTest {

    @Override
    protected List<Provider<Object>> getUniqueObjects() {
        List<Provider<Object>> result = newArrayList();
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new KeyStroke('Q');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new KeyStroke('S');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new KeyStroke(Modifiers.CTRL, 'Q');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new KeyStroke(Modifiers.SHIFT, 'Q');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new KeyStroke(Modifiers.SHIFT, 'S');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new KeyStroke(Modifiers.ALT, 'Q');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return alt('C');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return shift('C');
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return KeyStroke.ctrl('C');
            }
        });
        return result;
    }
}

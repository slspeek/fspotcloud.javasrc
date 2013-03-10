package com.googlecode.fspotcloud.keyboardaction;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class FlagConditionTest {

    FlagCondition flagCondition = new FlagCondition();


    @Test
    public void testExclude() throws Exception {
        flagCondition.exclude("Nah");
        assertTrue(flagCondition.holds(newHashSet("Foo")));
        assertFalse(flagCondition.holds(newHashSet("Nah")));
    }

    @Test
    public void testInclude() throws Exception {
        flagCondition.needs("Foo");
        assertTrue(flagCondition.holds(newHashSet("Foo")));
        assertFalse(flagCondition.holds(newHashSet("Nah")));
        assertTrue(flagCondition.holds(newHashSet("Boe", "Foo")));
    }
}

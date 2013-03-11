package com.googlecode.fspotcloud.keyboardaction;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class FlagsRuleTest {

    FlagsRule flagsRule = new FlagsRule();


    @Test
    public void testExclude() throws Exception {
        flagsRule.excludes("Nah");
        assertTrue(flagsRule.holds(newHashSet("Foo")));
        assertFalse(flagsRule.holds(newHashSet("Nah")));
    }

    @Test
    public void testInclude() throws Exception {
        flagsRule.needs("Foo");
        assertTrue(flagsRule.holds(newHashSet("Foo")));
        assertFalse(flagsRule.holds(newHashSet("Nah")));
        assertTrue(flagsRule.holds(newHashSet("Boe", "Foo")));
    }
}

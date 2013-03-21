package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Test;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FlagsRuleCloneTest {

    @Test
    public void testClone() throws Exception {
        FlagsRule ruleA = new FlagsRule().excludes("Bar");
        FlagsRule extendedRule = new FlagsRule(ruleA);
        extendedRule.excludes("Foo");
        assertTrue(ruleA.holds(newHashSet("Foo")));
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("FlagsRule{excludes=[], needed=[]}", new FlagsRule().toString());

    }
}

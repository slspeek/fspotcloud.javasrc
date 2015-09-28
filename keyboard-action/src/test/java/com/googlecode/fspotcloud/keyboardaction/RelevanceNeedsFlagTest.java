package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Sets;
import com.googlecode.fspotcloud.testharness.HomePlace;
import com.googlecode.fspotcloud.testharness.MainBuilder;
import com.googlecode.fspotcloud.testharness.OutPlace;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(JukitoRunner.class)
public class RelevanceNeedsFlagTest {

	Relevance relevance = new Relevance(HomePlace.class)
			.addDefaultKeys(KeyStroke.K);

	PlaceContext placeContext = new PlaceContext(OutPlace.class,
			Sets.<String> newHashSet(MainBuilder.FLAG_LOGGED_ON));

	FlagsRule condition;
	@Before
	public void setUp() throws Exception {
		condition = new FlagsRule();
		condition.needs(MainBuilder.FLAG_LOGGED_ON);
		assertTrue(condition.holds(placeContext.getFlags()));
		relevance.addRule(OutPlace.class, condition, KeyStroke.H);
	}

	@Test
	public void testGetKeys() throws Exception {
		List<KeyStroke> keys = relevance.getKeys(placeContext);
		assertEquals(1, keys.size());
	}
}

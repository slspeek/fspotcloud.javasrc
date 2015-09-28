package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Sets;
import com.googlecode.fspotcloud.testharness.HomePlace;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class RelevanceDefaultRuleTest {

	Relevance relevance = new Relevance(FlagsRule.needing("Foo"),
			HomePlace.class).addDefaultKeys(KeyStroke.K);
	PlaceContext placeContext = new PlaceContext(HomePlace.class,
			Sets.<String> newHashSet("Foo"));

	@Test
	public void testGetKeys() throws Exception {
		List<KeyStroke> keys = relevance.getKeys(placeContext);
		assertEquals(1, keys.size());
	}

	@Test
	public void testNoKeys() throws Exception {
		PlaceContext placeContext = new PlaceContext(HomePlace.class,
				Sets.<String> newHashSet());
		List<KeyStroke> keys = relevance.getKeys(placeContext);
		assertEquals(0, keys.size());
	}

	@Test
	public void testKeys() throws Exception {
		PlaceContext placeContext = new PlaceContext(HomePlace.class,
				Sets.<String> newHashSet("Foo", "Bar"));
		List<KeyStroke> keys = relevance.getKeys(placeContext);
		assertEquals(1, keys.size());
	}

	@Test
	public void testEmptyDefaultRule() throws Exception {
		Relevance relevance = new Relevance(FlagsRule.EMPTY, HomePlace.class)
				.addDefaultKeys(KeyStroke.K);
		PlaceContext placeContext = new PlaceContext(HomePlace.class,
				Sets.<String> newHashSet());
		List<KeyStroke> keys = relevance.getKeys(placeContext);
		assertEquals(1, keys.size());
	}

}

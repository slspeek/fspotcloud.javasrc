package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.test.EqualityTest;
import com.googlecode.fspotcloud.testharness.HomePlace;
import com.googlecode.fspotcloud.testharness.OutPlace;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class RelevanceEqualityTest extends EqualityTest {
	private FlagsRule fooRule = FlagsRule.needing("Foo");
	private FlagsRule barRule = FlagsRule.needing("Bar");
	private FlagsRule aRule = FlagsRule.excluding("Bar");

	@Override
	protected List<Provider<Object>> getUniqueObjects() {
		List<Provider<Object>> result = newArrayList();
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				Relevance relevance = new Relevance();
				return relevance;
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {

				Relevance relevance = new Relevance(fooRule);
				return relevance;
			}
		});

		result.add(new Provider<Object>() {
			@Override
			public Object get() {

				Relevance relevance = new Relevance(fooRule);
				relevance.addDefaultKeys(KeyStroke.ESC);
				return relevance;
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {

				Relevance relevance = new Relevance(fooRule);
				relevance.addDefaultKeys(KeyStroke.ESC);
				relevance.addRule(HomePlace.class, aRule, KeyStroke.F);
				return relevance;
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {

				Relevance relevance = new Relevance(fooRule);
				relevance.addDefaultKeys(KeyStroke.ESC);
				relevance.addRule(HomePlace.class, aRule, KeyStroke.F);
				relevance.addRule(OutPlace.class, barRule, KeyStroke.F);
				return relevance;
			}
		});
		return result;
	}

}

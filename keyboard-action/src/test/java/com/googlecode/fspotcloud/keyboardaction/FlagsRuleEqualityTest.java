package com.googlecode.fspotcloud.keyboardaction;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.test.EqualityTest;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class FlagsRuleEqualityTest extends EqualityTest {
	@Override
	protected List<Provider<Object>> getUniqueObjects() {
		List<Provider<Object>> result = newArrayList();
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				FlagsRule rule = new FlagsRule();
				return rule;

			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				FlagsRule rule = new FlagsRule();
				rule.excludes("Foo", "Bar");
				return rule;

			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				FlagsRule rule = new FlagsRule();
				rule.excludes("Bar");
				return rule;

			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				FlagsRule rule = new FlagsRule();
				rule.needs("Bar");
				return rule;

			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				FlagsRule rule = new FlagsRule();
				rule.needs("Bar", "Foo");
				return rule;
			}
		});
		return result;
	}
}

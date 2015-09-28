package com.googlecode.fspotcloud.shared.main.test;

import com.google.common.collect.Lists;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import com.googlecode.fspotcloud.test.EqualityTest;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class RequestFullsizeImageActionTest extends EqualityTest {
	@Override
	protected List<Provider<Object>> getUniqueObjects() {
		List<Provider<Object>> result = newArrayList();
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				return new RequestFullsizeImageAction(null);
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				return new RequestFullsizeImageAction("2");
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				return new RequestFullsizeImageAction("1");
			}
		});
		return result;
	}

	@Test
	public void testToString() throws Exception {
		assertEquals("RequestFullsizeImageAction{imageId=null}",
				new RequestFullsizeImageAction(null).toString());

	}
}

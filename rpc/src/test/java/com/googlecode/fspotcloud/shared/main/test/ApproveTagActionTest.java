package com.googlecode.fspotcloud.shared.main.test;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.shared.main.ApproveTagAction;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import com.googlecode.fspotcloud.test.EqualityTest;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class ApproveTagActionTest extends EqualityTest {
	@Test
	public void testToString() throws Exception {
		assertEquals("ApproveTagAction{tagId=null, usergroupId=0}",
				new ApproveTagAction(null, 0l).toString());
	}

	@Override
	protected List<Provider<Object>> getUniqueObjects() {
		List<Provider<Object>> result = newArrayList();
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				return new ApproveTagAction("1", 1000l);
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				return new ApproveTagAction(null, 1000l);
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				return new ApproveTagAction("1", 2000l);
			}
		});
		result.add(new Provider<Object>() {
			@Override
			public Object get() {
				return new ApproveTagAction("2", 2000l);
			}
		});

		return result;
	}
}

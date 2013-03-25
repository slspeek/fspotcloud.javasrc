package com.googlecode.fspotcloud.shared.main.test;

import com.google.common.collect.Lists;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.shared.main.EmailConfirmationAction;
import com.googlecode.fspotcloud.test.EqualityTest;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class EmailConfirmationActionTest extends EqualityTest {
    @Test
    public void testToString() throws Exception {
        assertEquals("EmailConfirmationAction{email=null, secret=null}", new EmailConfirmationAction(null, null).toString());
    }

    @Override
    protected List<Provider<Object>> getUniqueObjects() {
        List<Provider<Object>> result = newArrayList();
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new EmailConfirmationAction(null, null);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new EmailConfirmationAction("", null);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new EmailConfirmationAction("", "");
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new EmailConfirmationAction("foo", "");
            }
        });
        return result;
    }
}

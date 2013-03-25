package com.googlecode.fspotcloud.shared.main.test;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.shared.main.ApproveTagAction;
import com.googlecode.fspotcloud.shared.main.AuthenticationResult;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import com.googlecode.fspotcloud.test.EqualityTest;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class AuthenticationResultTest extends EqualityTest {
    @Test
    public void testToString() throws Exception {
        assertEquals("AuthenticationResult{success=false}"
                , new AuthenticationResult(false).toString());
    }

    @Override
    protected List<Provider<Object>> getUniqueObjects() {
        List<Provider<Object>> result = newArrayList();
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new AuthenticationResult(false);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new AuthenticationResult(true);
            }
        });
        return result;
    }
}

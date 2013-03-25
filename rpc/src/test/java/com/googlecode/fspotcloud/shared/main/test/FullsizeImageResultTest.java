package com.googlecode.fspotcloud.shared.main.test;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.shared.main.FullsizeImageResult;
import com.googlecode.fspotcloud.test.EqualityTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class FullsizeImageResultTest extends EqualityTest {
    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("FullsizeImageResult{message=null}", new FullsizeImageResult(null).toString());

    }

    @Override
    protected List<Provider<Object>> getUniqueObjects() {
        List<Provider<Object>> result = newArrayList();
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new FullsizeImageResult(null);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new FullsizeImageResult("");
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new FullsizeImageResult("foo");
            }
        });
        return result;
    }
}

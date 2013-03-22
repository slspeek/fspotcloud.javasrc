package com.googlecode.fspotcloud.client.place;

import com.google.inject.Provider;
import com.googlecode.fspotcloud.test.EqualityTest;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class BasePlaceEqualityTest extends EqualityTest {
    @Override
    protected List<Provider<Object>> getUniqueObjects() {
        List<Provider<Object>> result = newArrayList();
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace(null, null);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace("1", null);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace("2", null);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace("1", "1");
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace("1", "1", 2, 3, true);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace("1", "1", 3, 3, true);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace("1", "1", 3, 4, true);
            }
        });
        result.add(new Provider<Object>() {
            @Override
            public Object get() {
                return new BasePlace("1", "1", 3, 4);
            }
        });
        return result;
    }
}

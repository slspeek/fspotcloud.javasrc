package com.googlecode.fspotcloud.test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.fspotcloud.model.jpa.GaeCachedModelModule;

public class GaeFixtureRunner {
    public static void main(String[] args) {
        final LocalDatastoreServiceTestConfig testConfig = new LocalDatastoreServiceTestConfig();
        testConfig.setNoStorage(false);
        testConfig.setBackingStoreLocation("build/exploded/WEB-INF/appengine-generated/local_db.bin");
        LocalServiceTestHelper helper =
                new LocalServiceTestHelper(testConfig);
        helper.setUp();
        Injector injector = Guice.createInjector(new GaeCachedModelModule(100, "gae"));
        TwoUsersFixture twoUsersFixture = new TwoUsersFixture(injector);
        twoUsersFixture.run();
        //helper.tearDown();
    }
}

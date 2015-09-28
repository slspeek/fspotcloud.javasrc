package com.googlecode.fspotcloud.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googlecode.fspotcloud.model.jpa.J2eeModelModule;
import com.googlecode.simpleblobstore.j2ee.J2eeSimpleBlobstoreServletModule;

public class J2eeFixtureRunner {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new J2eeModelModule(100,
				"derby"), new J2eeSimpleBlobstoreServletModule());
		ThreeUsersFixture threeUsersFixture = new ThreeUsersFixture(injector);
		threeUsersFixture.run();
		System.exit(0);
	}
}

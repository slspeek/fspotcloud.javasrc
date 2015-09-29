package com.googlecode.fspotcloud.server.control.task.inject;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TaskModuleTest {

	@Test
	public void testInjector() throws Exception {
		Injector injector = Guice.createInjector(new TaskModule());
		assertNotNull(injector);
	}
}

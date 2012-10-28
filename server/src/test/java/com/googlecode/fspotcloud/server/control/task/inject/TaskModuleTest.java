package com.googlecode.fspotcloud.server.control.task.inject;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class TaskModuleTest {

    @Test
    public void testInjector() throws Exception {
        Injector injector = Guice.createInjector(new TaskModule());
        assertNotNull(injector);
    }
}

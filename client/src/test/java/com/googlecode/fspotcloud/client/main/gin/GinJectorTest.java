package com.googlecode.fspotcloud.client.main.gin;

import com.google.gwt.core.client.GWT;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTest;
import com.googlecode.gwt.test.gin.GInjectorCreateHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

@GwtModule("com.googlecode.fspotcloud.FSpotCloud")
public class GinJectorTest extends GwtTest {

    @Before
    public void setupGIN() {
        addGwtCreateHandler(new GInjectorCreateHandler());
    }

    @Test
    public void testInjectorCreation() throws Exception {
        final AppGinjector injector = GWT.create(AppGinjector.class);
        assertNotNull(injector);

    }
}

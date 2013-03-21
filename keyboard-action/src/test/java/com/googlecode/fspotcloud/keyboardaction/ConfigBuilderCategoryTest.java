package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class ConfigBuilderCategoryTest {

    public static class Module extends JukitoModule {

        @Override
        protected void configureTest() {
           bind(new TypeLiteral<List<ActionCategory>>(){}).toInstance(Lists.<ActionCategory>newArrayList());
        }
    }
    @Inject
    private ConfigBuilder configBuilder;

    private ActionCategory category;
    @Before
    public void setUp() throws Exception {
        category = configBuilder.createCategory("test");
    }


    @Test
    public void testGetActionCategoryList() throws Exception {
          assertEquals(1, configBuilder.getActionCategoryList().size());
    }


}

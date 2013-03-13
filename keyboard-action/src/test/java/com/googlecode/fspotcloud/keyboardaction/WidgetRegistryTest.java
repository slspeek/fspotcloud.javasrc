package com.googlecode.fspotcloud.keyboardaction;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WidgetRegistryTest {

    WidgetRegistry registry = new WidgetRegistry();


    @Test
    public void testSimple() throws Exception {
        ActionWidget actionWidget = new ActionWidget() {
            @Override
            public void onEvent(ActionDemoEvent event) {
            }

            @Override
            public void onEvent(ActionStateEvent event) {
            }
        };
        ActionWidget actionWidget2 = new ActionWidget() {
            @Override
            public void onEvent(ActionDemoEvent event) {
            }

            @Override
            public void onEvent(ActionStateEvent event) {
            }
        };
        registry.add("foo", actionWidget);
        registry.add("foo", actionWidget2);
        assertTrue(registry.get("foo").contains(actionWidget));
        assertTrue(registry.get("foo").contains(actionWidget2));
    }
}

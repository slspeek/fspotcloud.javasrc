package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;
import org.junit.Test;

import static org.junit.Assert.*;

public class HelpConfigTest {

    private SafeHtml optionalContent = new SafeHtml() {
        @Override
        public String asString() {
            return null;
        }
    };
    private HelpConfig helpConfig = new HelpConfig("Help", optionalContent);
    private ActionCategory screen = new ActionCategory("Screen");
    private ActionCategory keyboard = new ActionCategory("Keyboard");
    private ActionCategory navigation = new ActionCategory("Navigation");

    @Test
    public void testAddToFirstColumn() throws Exception {
        helpConfig.addToFirstColumn(screen);
        helpConfig.addToFirstColumn(keyboard);

        assertSame(screen, helpConfig.getFirstColumn().get(0));
        assertSame(keyboard, helpConfig.getFirstColumn().get(1));


    }

    @Test
    public void testAddToSecondColumn() throws Exception {
        helpConfig.addToSecondColumn(screen);
        helpConfig.addToSecondColumn(keyboard);

        assertSame(screen, helpConfig.getSecondColumn().get(0));
        assertSame(keyboard, helpConfig.getSecondColumn().get(1));
    }

    @Test
    public void testGetTitle() throws Exception {
        assertEquals("Help", helpConfig.getTitle());
    }


    @Test
    public void testGetOptionalContent() throws Exception {
        assertSame(optionalContent, helpConfig.getOptionalContent());
    }

    @Test public void testOtherConstructor() throws Exception {
        HelpConfig help = new HelpConfig("Simple");
        assertNull(help.getOptionalContent());
    }
}


package com.googlecode.fspotcloud.shared.main.test;

import com.googlecode.fspotcloud.shared.main.AuthenticationAction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AuthenticationActionTest {
    @Test
    public void testToString() throws Exception {
           assertEquals("AuthenticationAction{username=null, password not null=false}"
                   , new AuthenticationAction(null, null).toString());
        assertEquals("AuthenticationAction{username=null, password not null=true}"
                , new AuthenticationAction(null, "").toString());
    }
}

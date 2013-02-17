package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.shared.main.UpdateUserAction;
import com.googlecode.fspotcloud.shared.main.UpdateUserResult;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

@RunWith(JukitoRunner.class)
public class UpdateUserHandlerNotLoggedInTest {
    public static final String NEW_PASSWORD = "new password";
    public static final String OLD_PASSWORD = "old password";

    @Inject
    UpdateUserHandler handler;

    @Test(expected = UserIsNotLoggedOnException.class)
    public void testExecute() throws Exception {
        UpdateUserAction action = new UpdateUserAction(NEW_PASSWORD, OLD_PASSWORD);
        UpdateUserResult result = handler.execute(action, null);
    }
}

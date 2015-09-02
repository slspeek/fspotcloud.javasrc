package com.googlecode.fspotcloud.server.main.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.model.jpa.photo.PhotoEntity;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.fspotcloud.server.model.tag.IUserGroupHelper;
import com.googlecode.fspotcloud.shared.main.FullsizeImageResult;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import com.googlecode.fspotcloud.user.UserService;

@RunWith(JukitoRunner.class)
public class RequestFullsizeImageHandlerNotLogginInTest {


    public static final String ID = "1";
    @Inject
    private RequestFullsizeImageHandler handler;

    @Inject
    private ControllerDispatchAsync controllerAsyc;
    @Inject
    private PhotoDao photoDao;
    @Inject
    private UserService userService;
    @Inject
    private IMail mailer;
    @Inject
    private IUserGroupHelper userGroupHelper;

    private Photo photo = new PhotoEntity();
    private RequestFullsizeImageAction action = new RequestFullsizeImageAction(ID);


    @Before
    public void setUp() throws Exception {
        when(userService.isUserLoggedIn()).thenReturn(false);
    }

    @Test
    public void testExecute() throws Exception {
        FullsizeImageResult result = handler.execute(action, null);
        assertEquals(RequestFullsizeImageHandler.LOGON_FIRST, result.getMessage());
    }
}

package com.googlecode.fspotcloud.server.main.handler;

import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Set;

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
public class RequestFullsizeImageHandlerScheduledTest {


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
        photo.setId(ID);
        when(photoDao.find(ID)).thenReturn(photo);
        when(userService.isUserLoggedIn()).thenReturn(true);
        Set<String> empty = newHashSet();
        when(userGroupHelper.containsOneOf(empty)).thenReturn(true);
    }

    @Test
    public void testExecute() throws Exception {
        FullsizeImageResult result = handler.execute(action, null);
        assertEquals(RequestFullsizeImageHandler.SUCCESSFULLY_SCHEDULED, result.getMessage());
    }
}

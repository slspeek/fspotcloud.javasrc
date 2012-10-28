package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.model.jpa.usergroup.UserGroupEntity;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.NewUserGroupAction;
import com.googlecode.fspotcloud.user.UserService;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(JukitoRunner.class)
public class NewUserGroupHandlerTest {


    public static final String FOO = "FOO";
    @Inject
    private NewUserGroupHandler handler;

    @Inject
    private UserGroupDao userGroupDao;
    @Inject
    private UserService userService;

    private UserGroupEntity userGroupEntity = new UserGroupEntity();

    @Test
    public void testExecute() throws Exception {
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.getEmail()).thenReturn(FOO);
        when(userGroupDao.newEntity()).thenReturn(userGroupEntity);

        GetUserGroupResult result = handler.execute(new NewUserGroupAction(), null);
        assertEquals(NewUserGroupHandler.NEW_GROUP, userGroupEntity.getName());
        assertEquals(NewUserGroupHandler.NO_DESCRIPTION, userGroupEntity.getDescription());
        verify(userService).isUserLoggedIn();
        verify(userService).getEmail();
        verify(userGroupDao).newEntity();
        verify(userGroupDao).save(userGroupEntity);
    }

    @Test(expected = UserIsNotLoggedException.class)
    public void testNotLoggedIn() throws Exception {
        when(userService.isUserLoggedIn()).thenReturn(false);
        GetUserGroupResult result = handler.execute(new NewUserGroupAction(), null);
    }

}

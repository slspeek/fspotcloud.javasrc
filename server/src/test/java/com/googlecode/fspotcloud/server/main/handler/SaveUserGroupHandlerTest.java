package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.model.jpa.usergroup.UserGroupEntity;
import com.googlecode.fspotcloud.server.model.api.UserGroup;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.shared.main.SaveUserGroupAction;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class SaveUserGroupHandlerTest {

    public static final String FOO = "FOO";
    public static final String BAR = "Bar";
    public static final String CLOSE_FAMILY = "Close family";
    public static final String FAMILY = "Family";
    @Inject
    private SaveUserGroupHandler handler;
    @Inject
    private UserService userService;
    @Inject
    private UserGroupDao userGroupDao;

    @Test(expected = UserIsNotLoggedException.class)
    public void testUnauthorized() throws Exception {

        handler.execute(new SaveUserGroupAction(new UserGroupInfo(1L, FAMILY, CLOSE_FAMILY, false)), null);
        verify(userService).isUserLoggedIn();
        verifyNoMoreInteractions(userGroupDao, userService);
    }

    @Test(expected = UsergroupNotFoundException.class)
    public void testGroupDoesNotExist() throws Exception {
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.getEmail()).thenReturn("FOO");
        handler.execute(new SaveUserGroupAction(new UserGroupInfo(1L, FAMILY, CLOSE_FAMILY, false)), null);
        verify(userService).isUserLoggedIn();
        verify(userService).getEmail();
        verify(userGroupDao).find(1L);
        verifyNoMoreInteractions(userGroupDao, userService);
    }

    @Test(expected = UserIsNotOwnerException.class)
    public void testNotOwner() throws DispatchException {
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.getEmail()).thenReturn(FOO);
        UserGroup userGroup = new UserGroupEntity();
        userGroup.setOwner(BAR);
        when(userGroupDao.find(1L)).thenReturn(userGroup);
        handler.execute(new SaveUserGroupAction(new UserGroupInfo(1L, FAMILY, CLOSE_FAMILY, false)), null);
        verify(userService).isUserLoggedIn();
        verify(userService).getEmail();
        verify(userGroupDao).find(1L);
        verifyNoMoreInteractions(userGroupDao, userService);
    }

    @Test
    public void testSuccess() throws DispatchException {
        when(userService.isUserLoggedIn()).thenReturn(true);
        when(userService.getEmail()).thenReturn(FOO);
        UserGroup userGroup = new UserGroupEntity();
        userGroup.setOwner(FOO);
        when(userGroupDao.find(1L)).thenReturn(userGroup);
        handler.execute(new SaveUserGroupAction(new UserGroupInfo(1L, FAMILY, CLOSE_FAMILY, false)), null);
        verify(userService).isUserLoggedIn();
        verify(userService).getEmail();
        verify(userGroupDao).find(1L);

        verify(userGroupDao).save(userGroup);
        assertEquals(userGroup.getDescription(), CLOSE_FAMILY);
        assertEquals(userGroup.getName(), FAMILY);
        verifyNoMoreInteractions(userGroupDao, userService);
    }
}

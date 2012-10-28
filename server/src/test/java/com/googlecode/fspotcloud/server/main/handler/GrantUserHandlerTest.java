package com.googlecode.fspotcloud.server.main.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.model.jpa.user.UserEntity;
import com.googlecode.fspotcloud.model.jpa.usergroup.UserGroupEntity;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;
import com.googlecode.fspotcloud.server.model.api.UserGroup;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.shared.main.GrantUserAction;
import com.googlecode.fspotcloud.user.IAdminPermission;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class GrantUserHandlerTest {

    public static final String USER_ID = "slspeek@gmail.com";
    public static final long USER_GROUP_ID = 1L;
    @Inject
    GrantUserHandler handler;

    @Inject
    UserGroupDao userGroupDao;
    @Inject
    UserDao userDao;
    @Inject
    IAdminPermission adminPermission;
    private final GrantUserAction action = new GrantUserAction(USER_ID, USER_GROUP_ID);
    private final User user = new UserEntity(USER_ID);
    private final UserGroup userGroup = new UserGroupEntity();


    @Test
    public void testExecute() throws Exception {
        when(userGroupDao.find(USER_GROUP_ID)).thenReturn(userGroup);
        when(userDao.findOrNew(USER_ID)).thenReturn(user);
        handler.execute(action, null);
        assertTrue(user.getGrantedUserGroups().contains(USER_GROUP_ID));
        assertTrue(userGroup.getGrantedUsers().contains(USER_ID));
        verify(userDao).findOrNew(USER_ID);
        verify(userGroupDao).find(USER_GROUP_ID);
        verify(userDao).save(user);
        verify(userGroupDao).save(userGroup);
        verifyNoMoreInteractions(userDao, userGroupDao);

    }

    @Test(expected = DispatchException.class)
    public void testUsergroupNull() throws Exception {
        when(userDao.findOrNew(USER_ID)).thenReturn(user);
        handler.execute(action, null);
        assertFalse(user.getGrantedUserGroups().contains(USER_GROUP_ID));
        assertFalse(userGroup.getApprovedTagIds().contains(USER_ID));
    }

    @Test(expected = SecurityException.class)
    public void testExecuteNXS() throws Exception {
        doThrow(new SecurityException()).when(adminPermission).checkAdminPermission();
        handler.execute(action, null);
        assertFalse(user.getGrantedUserGroups().contains(USER_GROUP_ID));
        assertFalse(userGroup.getApprovedTagIds().contains(USER_ID));
    }
}

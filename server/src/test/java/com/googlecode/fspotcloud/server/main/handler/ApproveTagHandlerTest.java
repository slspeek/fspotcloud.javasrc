package com.googlecode.fspotcloud.server.main.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.model.jpa.tag.TagEntity;
import com.googlecode.fspotcloud.model.jpa.usergroup.UserGroupEntity;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.server.model.api.UserGroup;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.shared.main.ApproveTagAction;
import com.googlecode.fspotcloud.user.IAdminPermission;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA.
 * User: steven
 * Date: 19-8-12
 * Time: 21:28
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JukitoRunner.class)
public class ApproveTagHandlerTest {

    public static final String TAG_ID = "1";
    public static final long USER_GROUP_ID = 1L;
    @Inject
    ApproveTagHandler handler;

    @Inject
    UserGroupDao userGroupDao;
    @Inject
    TagDao tagDao;
    @Inject
    IAdminPermission adminPermission;
    private final ApproveTagAction action = new ApproveTagAction(TAG_ID, USER_GROUP_ID);
    private final Tag tag = new TagEntity();
    private final UserGroup userGroup = new UserGroupEntity();


    @Test
    public void testExecute() throws Exception {
        when(userGroupDao.find(USER_GROUP_ID)).thenReturn(userGroup);
        when(tagDao.find(TAG_ID)).thenReturn(tag);
        handler.execute(action, null);
        assertTrue(tag.getApprovedUserGroups().contains(USER_GROUP_ID));
        assertTrue(userGroup.getApprovedTagIds().contains(TAG_ID));
        verify(tagDao).find(TAG_ID);
        verify(userGroupDao).find(USER_GROUP_ID);
        verify(tagDao).save(tag);
        verify(userGroupDao).save(userGroup);
        verifyNoMoreInteractions(tagDao, userGroupDao);

    }

    @Test(expected = UsergroupNotFoundException.class)
    public void testUsergroupNull() throws Exception {
        when(tagDao.find(TAG_ID)).thenReturn(tag);
        handler.execute(action, null);
        assertFalse(tag.getApprovedUserGroups().contains(USER_GROUP_ID));
        assertFalse(userGroup.getApprovedTagIds().contains(TAG_ID));
    }

    @Test(expected = TagNotFoundException.class)
    public void testTagNull() throws Exception {
        when(userGroupDao.find(USER_GROUP_ID)).thenReturn(userGroup);
        handler.execute(action, null);
        assertFalse(tag.getApprovedUserGroups().contains(USER_GROUP_ID));
        assertFalse(userGroup.getApprovedTagIds().contains(TAG_ID));
    }

    @Test(expected = SecurityException.class)
    public void testExecuteNXS() throws Exception {
        doThrow(new SecurityException()).when(adminPermission).checkAdminPermission();
        handler.execute(action, null);
        assertFalse(tag.getApprovedUserGroups().contains(USER_GROUP_ID));
        assertFalse(userGroup.getApprovedTagIds().contains(TAG_ID));
    }
}

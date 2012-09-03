package com.googlecode.fspotcloud.shared.main;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: steven
 * Date: 19-8-12
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
public class ApproveTagActionTest {

    public static final String FIRST_ARG = "firstArg";
    public static final long USER_GROUP_ID = 1234L;

    @Test
    public void testGetTagId() throws Exception {
        ApproveTagAction action = new ApproveTagAction(FIRST_ARG, USER_GROUP_ID);
        assertEquals(FIRST_ARG, action.getTagId());
        assertTrue(USER_GROUP_ID == action.getUsergroupId());
    }

    public void testToString() throws Exception {
        ApproveTagAction action = new ApproveTagAction(FIRST_ARG, USER_GROUP_ID);
        assertNotNull(action.toString());
    }

    public void testEquals() throws Exception {
        ApproveTagAction action = new ApproveTagAction(FIRST_ARG, USER_GROUP_ID);
        ApproveTagAction action2 = new ApproveTagAction(FIRST_ARG, USER_GROUP_ID);
        assertEquals(action, action2);
    }

    public void testNotSame() throws Exception {
        ApproveTagAction action = new ApproveTagAction(FIRST_ARG, USER_GROUP_ID);
        ApproveTagAction action2 = new ApproveTagAction(FIRST_ARG, 0L);
        assertFalse(action.equals(action2));
    }


}

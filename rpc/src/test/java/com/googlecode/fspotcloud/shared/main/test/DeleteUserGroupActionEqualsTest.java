package com.googlecode.fspotcloud.shared.main.test;

import com.googlecode.fspotcloud.shared.main.DeleteGroupAction;
import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeleteUserGroupActionEqualsTest extends EqualsTest {

    public static final long ID = 10L;
    public static final long ID1 = 2L;

    @Test
    public void testGetId() throws Exception {

    }

    @Override
    protected Object getOne() {
        return new DeleteGroupAction(ID);
    }

    @Override
    protected Object getTheOther() {
        return new DeleteGroupAction(ID);
    }

    @Override
    protected Object getDifferentOne() {
        return new DeleteGroupAction(ID1);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals("DeleteGroupAction{groupId=1}", new DeleteGroupAction(1l).toString());

    }
}

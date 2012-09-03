package com.googlecode.fspotcloud.shared.main;

import com.googlecode.fspotcloud.test.EqualsTest;
import org.junit.Test;

public class DeleteUserGroupActionEqualsTest extends EqualsTest {

    public static final long ID = 10L;
    public static final long ID1 = 2L;

    @Test
    public void testGetId() throws Exception {

    }

    @Override
    protected Object getOne() {
        return new DeleteUserGroupAction(ID);
    }

    @Override
    protected Object getTheOther() {
        return new DeleteUserGroupAction(ID);
    }

    @Override
    protected Object getDifferentOne() {
        return new DeleteUserGroupAction(ID1);
    }
}

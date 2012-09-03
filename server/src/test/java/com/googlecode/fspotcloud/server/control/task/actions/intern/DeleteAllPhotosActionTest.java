package com.googlecode.fspotcloud.server.control.task.actions.intern;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeleteAllPhotosActionTest {
    DeleteAllPhotosAction action1 = new DeleteAllPhotosAction();
    DeleteAllPhotosAction action2 = new DeleteAllPhotosAction();

    @Test
    public void testEquals() throws Exception {
        assertEquals(action1, action2);

    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(DeleteAllPhotosAction.ANSWER_TO_EVERTHING, action1.hashCode());
    }
}

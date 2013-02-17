package com.googlecode.fspotcloud.server.main.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RssFeedAction;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagNodeTestFactory;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import net.customware.gwt.dispatch.server.Dispatch;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class RssFeedHandlerTest {

    public static final String TAG_ID = "1";
    @Inject
    private Dispatch dispatch;

    @Inject
    private RssFeedHandler handler;

    private RssFeedAction action = new RssFeedAction(TAG_ID);
    private GetTagTreeAction tagTreeAction = new GetTagTreeAction();

    private TagNodeTestFactory factory = new TagNodeTestFactory();
    private TagNode node = factory.getSingleNodeWithOnePicture();

    @Test
    public void testExistingCategory() throws Exception {
        TagTreeResult tagTreeResult = new TagTreeResult(node);
        when(dispatch.execute(tagTreeAction)).thenReturn(tagTreeResult);
        String rss = handler.execute(action, null).get();
        assertTrue(rss.contains("/#BasePlace:1:42:1:1"));
    }

    @Test
    public void testNonExistingCategory() throws Exception {
        TagTreeResult tagTreeResult = new TagTreeResult(node);
        action = new RssFeedAction("2");
        when(dispatch.execute(tagTreeAction)).thenReturn(tagTreeResult);
        String rss = handler.execute(action, null).get();
        assertTrue(rss.contains("Not found"));
    }
}

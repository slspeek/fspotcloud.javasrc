package com.googlecode.fspotcloud.client.data;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagNodeTestFactory;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class LatestTagHelperTest {

    @Inject
    private LatestTagHelper latestTagHelper;
    @Inject
    private TagNodeTestFactory factory;
    private TagNode fullTree;

    @Before
    public void setUp() throws Exception {
        fullTree = factory.getTwoCategories();

    }

    @Test
    public void testGetLatestTag() throws Exception {
        final TagNode latestNode = latestTagHelper.getLatestNode(fullTree);
        assertEquals("2", latestNode.getId());

    }

    @Test
    public void testGetLatestTagSimple() throws Exception {
        fullTree = factory.getSingleNodeWithOnePicture();
        final TagNode latestNode = latestTagHelper.getLatestNode(fullTree);
        assertEquals("1", latestNode.getId());
    }
}

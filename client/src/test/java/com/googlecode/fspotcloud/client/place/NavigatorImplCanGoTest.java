package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagNodeTestFactory;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class NavigatorImplCanGoTest {


    private static final String TAG_ID = "1";
    @Inject
    private  DataManager dataManager;
    @Inject
    private NavigatorImpl navigator;
    @Inject
    private TagNodeTestFactory tagNodeTestFactory;
    @Inject
    private ArgumentCaptor<AsyncCallback<TagNode>> nodeCaptor;

    @Inject
    private AsyncCallback<Boolean> answerCallback;
    @Inject
    private IPlaceController placeController;


    @Test
    public void testCanGoForwardOneImage() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);

        verify(answerCallback).onSuccess(false);
     }

    @Test
    public void testCanGoForwardTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);

        verify(answerCallback).onSuccess(true);
    }

    @Test
    public void testCanGoBackOneImage() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.BACKWARD, Navigator.Unit.SINGLE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);

        verify(answerCallback).onSuccess(false);
    }


    @Test
    public void testCanGoBorderOneImage() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.BACKWARD, Navigator.Unit.BORDER, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
        callback.onSuccess(tree);
        verify(answerCallback).onSuccess(false);
    }

    @Test
    public void testCanGoBorderTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.FORWARD, Navigator.Unit.BORDER, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);
        verify(answerCallback).onSuccess(true);
    }


    @Test
    public void testCanGoBackTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.BACKWARD, Navigator.Unit.SINGLE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);

        verify(answerCallback).onSuccess(false);
    }

    @Test
    public void testCanGoPageUpTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.BACKWARD, Navigator.Unit.PAGE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);
        verify(answerCallback).onSuccess(false);
    }

    @Test
    public void testCanGoPageDownTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(Navigator.Direction.FORWARD, Navigator.Unit.PAGE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);
        verify(answerCallback).onSuccess(true);
    }
}

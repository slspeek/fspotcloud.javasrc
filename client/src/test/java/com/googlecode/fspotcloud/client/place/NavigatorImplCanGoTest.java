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

import static com.googlecode.fspotcloud.client.place.api.Navigator.Direction.BACKWARD;
import static com.googlecode.fspotcloud.client.place.api.Navigator.Direction.FORWARD;
import static com.googlecode.fspotcloud.client.place.api.Navigator.Unit.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class NavigatorImplCanGoTest {


    private static final String TAG_ID = "1";
    @Inject
    private DataManager dataManager;
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


    private void canGo(BasePlace from,
                       Navigator.Direction direction,
                       Navigator.Unit unit,
                       Boolean expectedResult,
                       TagNode tagNode) {
        reset(dataManager, placeController, answerCallback);


        when(placeController.where()).thenReturn(from);
        navigator.canGoAsync(direction, unit, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        callback.onSuccess(tagNode);
        verify(answerCallback).onSuccess(expectedResult);
    }

    private void canGoOneImage(Navigator.Direction direction,
                               Navigator.Unit unit,
                               Boolean expectedResult
                               ) {
        canGo(new BasePlace(TAG_ID, "42"),
                direction,
                unit,
                expectedResult,
                tagNodeTestFactory.getSingleNodeWithOnePicture());

    }

    private void cannotGoOneImageFromUnkownPhoto(Navigator.Direction direction,
                               Navigator.Unit unit)

    {
        canGo(new BasePlace(TAG_ID, "101"),
                direction,
                unit,
                false,
                tagNodeTestFactory.getSingleNodeWithOnePicture());

    }



    private void canGoTwoImageFromHome(Navigator.Direction direction,
                               Navigator.Unit unit,
                               Boolean expectedResult
    ) {
        canGo(new BasePlace(TAG_ID, "42"),
                direction,
                unit,
                expectedResult,
                tagNodeTestFactory.getSingleNodeWithTwoPictures());

    }

    private void canGoTwoImageFromEnd(Navigator.Direction direction,
                                       Navigator.Unit unit,
                                       Boolean expectedResult
    ) {
        canGo(new BasePlace(TAG_ID, "102"),
                direction,
                unit,
                expectedResult,
                tagNodeTestFactory.getSingleNodeWithTwoPictures());

    }

    @Test
    public void testCannotMoveFromUnkownPhoto() throws Exception {
        for (Navigator.Direction direction : Navigator.Direction.values()) {
            for (Navigator.Unit unit : Navigator.Unit.values()) {
                cannotGoOneImageFromUnkownPhoto(direction, unit);
            }
        }
    }

    @Test
    public void testOneImage() throws Exception {
        canGoOneImage(
                FORWARD,
                SINGLE,
                false
                );
        canGoOneImage(
                FORWARD,
                ROW,
                false
        );
        canGoOneImage(
                FORWARD,
                PAGE,
                false
        );
        canGoOneImage(
                FORWARD,
                BORDER,
                false
        );
        canGoOneImage(
                BACKWARD,
                SINGLE,
                false
        );
        canGoOneImage(
                BACKWARD,
                ROW,
                false
        );
        canGoOneImage(
                BACKWARD,
                PAGE,
                false
        );
        canGoOneImage(
                BACKWARD,
                BORDER,
                false
        );


    }

    @Test
    public void testTwoImagesHome() throws Exception {
        canGoTwoImageFromHome(
                FORWARD,
                SINGLE,
                true
        );
        canGoTwoImageFromHome(
                FORWARD,
                ROW,
                true
        );
        canGoTwoImageFromHome(
                FORWARD,
                PAGE,
                true
        );
        canGoTwoImageFromHome(
                FORWARD,
                BORDER,
                true
        );
        canGoTwoImageFromHome(
                BACKWARD,
                SINGLE,
                false
        );
        canGoTwoImageFromHome(
                BACKWARD,
                ROW,
                false
        );
        canGoTwoImageFromHome(
                BACKWARD,
                PAGE,
                false
        );
        canGoTwoImageFromHome(
                BACKWARD,
                BORDER,
                false
        );


    }

    @Test
    public void testTwoImagesEnd() throws Exception {
        canGoTwoImageFromEnd(
                FORWARD,
                SINGLE,
                false
        );
        canGoTwoImageFromEnd(
                FORWARD,
                ROW,
                false
        );
        canGoTwoImageFromEnd(
                FORWARD,
                PAGE,
                false
        );
        canGoTwoImageFromEnd(
                FORWARD,
                BORDER,
                false
        );
        canGoTwoImageFromEnd(
                BACKWARD,
                SINGLE,
                true
        );
        canGoTwoImageFromEnd(
                BACKWARD,
                ROW,
                true
        );
        canGoTwoImageFromEnd(
                BACKWARD,
                PAGE,
                true
        );
        canGoTwoImageFromEnd(
                BACKWARD,
                BORDER,
                true
        );
    }

    @Test
    public void testCanGoForwardTwoImages() throws Exception {
        canGo(new BasePlace(TAG_ID, "42"),
                FORWARD,
                SINGLE,
                true,
                tagNodeTestFactory.getSingleNodeWithTwoPictures());


    }

    @Test
    public void testCanGoBackOneImage() throws Exception {
        canGo(new BasePlace(TAG_ID, "42"),
                BACKWARD,
                SINGLE,
                false,
                tagNodeTestFactory.getSingleNodeWithOnePicture());

    }

    @Test
    public void testCanNotGoHomeOneImage() throws Exception {
        canGo(new BasePlace(TAG_ID, "42"),
                BACKWARD,
                BORDER,
                false,
                tagNodeTestFactory.getSingleNodeWithOnePicture());
    }


    @Test
    public void testCanGoBorderTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(FORWARD, BORDER, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);
        verify(answerCallback).onSuccess(true);
    }


    @Test
    public void testCanGoBackTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(BACKWARD, SINGLE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);

        verify(answerCallback).onSuccess(false);
    }

    @Test
    public void testCanGoPageUpTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(BACKWARD, PAGE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);
        verify(answerCallback).onSuccess(false);
    }

    @Test
    public void testCanGoPageDownTwoImages() throws Exception {
        when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
        navigator.canGoAsync(FORWARD, PAGE, answerCallback);
        verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
        AsyncCallback<TagNode> callback = nodeCaptor.getValue();
        TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
        callback.onSuccess(tree);
        verify(answerCallback).onSuccess(true);
    }
}

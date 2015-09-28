package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.test.TagNodeTestFactory;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(JukitoRunner.class)
public class NavigatorImplGoTest {

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
	private IPlaceController placeController;

	@Test
	public void testGoForwardNoWhere() throws Exception {
		//when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		navigator.goAsync(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE);
		verifyZeroInteractions(dataManager);

	}

	@Test
	public void testGoForwardTwoImages() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		navigator.goAsync(Navigator.Direction.FORWARD, Navigator.Unit.SINGLE);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);
		verify(placeController).goTo(
				new BasePlace(TAG_ID, TagNodeTestFactory.SECOND_PICTURE_INFO
						.getId()));
	}

	@Test
	public void testGoBackTwoImages() throws Exception {
		when(placeController.where()).thenReturn(
				new BasePlace(TAG_ID, TagNodeTestFactory.SECOND_PICTURE_INFO
						.getId()));
		navigator.goAsync(Navigator.Direction.BACKWARD, Navigator.Unit.SINGLE);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);
		verify(placeController).goTo(new BasePlace(TAG_ID, "42"));

	}

	@Test
	public void testGoEndOneImage() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		navigator.goAsync(Navigator.Direction.FORWARD, Navigator.Unit.BORDER);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
		callback.onSuccess(tree);
		verify(placeController).where();
		verify(placeController).goTo(new BasePlace(TAG_ID, "42"));
	}

	@Test
	public void testGoEndTwoImages() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		navigator.goAsync(Navigator.Direction.FORWARD, Navigator.Unit.BORDER);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);
		verify(placeController).goTo(
				new BasePlace(TAG_ID, TagNodeTestFactory.SECOND_PICTURE_INFO
						.getId()));

	}

	@Test
	public void testCanGoRowDownTwoImages() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		navigator.goAsync(Navigator.Direction.FORWARD, Navigator.Unit.ROW);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);
		verify(placeController).goTo(
				new BasePlace(TAG_ID, TagNodeTestFactory.SECOND_PICTURE_INFO
						.getId()));
	}

	@Test
	public void testCanGoPageUpTwoImages() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		navigator.goAsync(Navigator.Direction.BACKWARD, Navigator.Unit.PAGE);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);
	}

	@Test
	public void testCanGoPageDownTwoImages() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		navigator.goAsync(Navigator.Direction.FORWARD, Navigator.Unit.PAGE);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);
		verify(placeController).goTo(
				new BasePlace(TAG_ID, TagNodeTestFactory.SECOND_PICTURE_INFO
						.getId()));
	}
}

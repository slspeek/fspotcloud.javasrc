package com.googlecode.fspotcloud.client.place;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.test.TagNodeTestFactory;

@RunWith(JukitoRunner.class)
public class NavigatorImplGetPageRelativePositionTest {

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
	private AsyncCallback<Integer[]> answerCallback;
	@Inject
	private ArgumentCaptor<Integer[]> answerCaptor;

	@Test
	public void testPageRelative() throws Exception {
		navigator.getPageRelativePositionAsync(TAG_ID, "42", 1, answerCallback);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithOnePicture();
		callback.onSuccess(tree);

		verify(answerCallback).onSuccess(answerCaptor.capture());
		Integer[] answer = answerCaptor.getValue();
		assertEquals(0, answer[0].intValue());
		assertEquals(1, answer[1].intValue());

	}

	@Test
	public void testPageRelativeNullNode() throws Exception {
		navigator.getPageRelativePositionAsync(TAG_ID, "42", 1, answerCallback);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();

		callback.onSuccess(null);

		verify(answerCallback).onFailure(any(RuntimeException.class));

	}

	@Test
	public void testPageRelativeTwoImages() throws Exception {
		navigator.getPageRelativePositionAsync(TAG_ID, "42", 1, answerCallback);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);

		verify(answerCallback).onSuccess(answerCaptor.capture());
		Integer[] answer = answerCaptor.getValue();
		assertEquals(0, answer[0].intValue());
		assertEquals(2, answer[1].intValue());
	}

}

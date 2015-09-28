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

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class NavigatorImplPossibleMovesTest {

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
	private AsyncCallback<Map<Move, Boolean>> answerCallback;
	@Inject
	private IPlaceController placeController;
	@Inject
	private ArgumentCaptor<Map<Move, Boolean>> answerCaptor;

	@Test
	public void testPossibleMovesTwoImages() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "42"));
		Map<Move, Boolean> moves = getMoveBooleanMap();
		assertTrue(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.SINGLE)));
		assertTrue(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.ROW)));
		assertTrue(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.PAGE)));
		assertTrue(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.BORDER)));
		assertFalse(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.SINGLE)));
		assertFalse(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.ROW)));
		assertFalse(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.PAGE)));
		assertFalse(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.BORDER)));
	}

	public void testPossibleMovesTwoImagesEnd() throws Exception {
		when(placeController.where()).thenReturn(new BasePlace(TAG_ID, "102"));
		Map<Move, Boolean> moves = getMoveBooleanMap();
		assertFalse(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.SINGLE)));
		assertFalse(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.ROW)));
		assertFalse(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.PAGE)));
		assertFalse(moves.get(new Move(Navigator.Direction.FORWARD,
				Navigator.Unit.BORDER)));
		assertTrue(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.SINGLE)));
		assertTrue(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.ROW)));
		assertTrue(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.PAGE)));
		assertTrue(moves.get(new Move(Navigator.Direction.BACKWARD,
				Navigator.Unit.BORDER)));
	}

	private Map<Move, Boolean> getMoveBooleanMap() {
		navigator.getPossibleMoves(answerCallback);
		verify(dataManager).getTagNode(any(String.class), nodeCaptor.capture());
		AsyncCallback<TagNode> callback = nodeCaptor.getValue();
		TagNode tree = tagNodeTestFactory.getSingleNodeWithTwoPictures();
		callback.onSuccess(tree);

		verify(answerCallback).onSuccess(answerCaptor.capture());
		return answerCaptor.getValue();
	}
}

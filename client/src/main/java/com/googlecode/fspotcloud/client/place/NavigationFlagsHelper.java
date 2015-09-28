package com.googlecode.fspotcloud.client.place;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class NavigationFlagsHelper {

	@Inject
	private Navigator navigator;
	@Inject
	private IModeController modeController;
	@Inject
	private MoveActionMap moveActionMap;

	private class CallbackHelper implements AsyncCallback<Map<Move, Boolean>> {

		@Override
		public void onFailure(Throwable caught) {

		}

		@Override
		public void onSuccess(Map<Move, Boolean> moveBooleanMap) {
			Map<String, Boolean> map = newHashMap();
			for (Move move : moveBooleanMap.keySet()) {
				map.put(moveActionMap.getActionId(move),
						moveBooleanMap.get(move));
			}
			modeController.setFlags(map);
		}
	}

	public void update() {
		navigator.getPossibleMoves(new CallbackHelper());
	}

}

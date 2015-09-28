package com.googlecode.fspotcloud.client.enduseraction.dashboard.handler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.ConfirmationDialogBox;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.UserDeletesAllAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveAllHandler implements IActionHandler {
	private final DispatchAsync dispatcher;
	private final Logger log = Logger.getLogger(RemoveAllHandler.class
			.getName());
	private final ConfirmationDialogBox box;

	@Inject
	public RemoveAllHandler(DispatchAsync dispatcher, ConfirmationDialogBox box) {
		this.dispatcher = dispatcher;
		this.box = box;
	}

	@Override
	public void performAction(String actionId) {
		box.setText("Confirm remove all");
		box.center();
		box.setConfirmAction(new Runnable() {
			@Override
			public void run() {
				dispatcher.execute(new UserDeletesAllAction(),
						new AsyncCallback<VoidResult>() {
							@Override
							public void onFailure(Throwable caught) {
								log.log(Level.SEVERE, "Action Exception ",
										caught);
							}

							@Override
							public void onSuccess(VoidResult result) {
							}
						});
			}
		});

	}
}

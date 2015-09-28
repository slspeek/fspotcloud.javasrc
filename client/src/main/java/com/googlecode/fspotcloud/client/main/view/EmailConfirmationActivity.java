package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.EmailConfirmationView;
import com.googlecode.fspotcloud.client.place.EmailConfirmationPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.EmailConfirmationAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

public class EmailConfirmationActivity extends AbstractActivity
		implements
			EmailConfirmationView.EmailConfirmationPresenter {

	private final EmailConfirmationView emailConfirmationView;
	private final DispatchAsync dispatchAsync;
	private final IPlaceController placeController;

	@Inject
	public EmailConfirmationActivity(
			EmailConfirmationView emailConfirmationView,
			DispatchAsync dispatchAsync, IPlaceController placeController) {
		this.emailConfirmationView = emailConfirmationView;
		this.dispatchAsync = dispatchAsync;
		this.placeController = placeController;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		panel.setWidget(emailConfirmationView);
		EmailConfirmationPlace place = (EmailConfirmationPlace) placeController
				.getRawWhere();
		String email = place.getEmail();
		String secret = place.getSecret();
		emailConfirmationView.setStatusText("Verifying for: " + email);
		dispatchAsync.execute(new EmailConfirmationAction(email, secret),
				new AsyncCallback<VoidResult>() {
					@Override
					public void onFailure(Throwable caught) {
						emailConfirmationView.setStatusText("Failure");
					}

					@Override
					public void onSuccess(VoidResult result) {
						emailConfirmationView.setStatusText("Success");
					}
				});

	}
}

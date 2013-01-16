package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.EmailConfirmationView;
import com.googlecode.fspotcloud.client.place.EmailConfirmationPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.EmailConfirmationAction;
import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.shared.AbstractSimpleResult;

public class EmailConfirmationActivity extends AbstractActivity implements EmailConfirmationView.EmailConfirmationPresenter {

    private final EmailConfirmationView emailConfirmationView;
    private final DispatchAsync dispatchAsync;
    private final PlaceWhere placeWhere;



    @Inject
    public EmailConfirmationActivity(EmailConfirmationView emailConfirmationView,
                                     DispatchAsync dispatchAsync,
                                     PlaceWhere placeWhere) {
        this.emailConfirmationView = emailConfirmationView;
        this.dispatchAsync = dispatchAsync;
        this.placeWhere = placeWhere;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        panel.setWidget(emailConfirmationView);
        EmailConfirmationPlace place = (EmailConfirmationPlace) placeWhere.getRawWhere();
        String email = place.getEmail();
        String secret = place.getSecret();
        emailConfirmationView.setStatusText("Verifying for: " + email);
        dispatchAsync.execute(new EmailConfirmationAction(email, secret), new AsyncCallback<VoidResult>() {
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

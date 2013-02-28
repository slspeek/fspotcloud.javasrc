package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.main.view.api.ImagePresenterFactory;
import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.client.main.view.api.MailFullsizeView;
import com.googlecode.fspotcloud.client.place.MailFullsizePlace;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.FullsizeImageResult;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MailFullsizeActivity extends AbstractActivity implements MailFullsizeView.MailFullsizePresenter {

    private final Logger log = Logger.getLogger(MailFullsizeActivity.class.getName());
    private final MailFullsizeView mailFullsizeView;
    private final DispatchAsync dispatchAsync;
    private final ImagePresenterFactory imagePresenterFactory;
    private final Navigator navigator;
    private final IScheduler scheduler;
    private final UserActions userActions;
    private MailFullsizePlace mailFullsizePlace;

    @Inject
    public MailFullsizeActivity(
            MailFullsizeView mailFullsizeView,
            DispatchAsync dispatchAsync,
            ImagePresenterFactory imagePresenterFactory,
            Navigator navigator,
            IScheduler scheduler,
            UserActions userActions) {
        this.mailFullsizeView = mailFullsizeView;
        this.dispatchAsync = dispatchAsync;
        this.imagePresenterFactory = imagePresenterFactory;
        this.navigator = navigator;
        this.scheduler = scheduler;
        this.userActions = userActions;
    }

    public void setMailFullsizePlace(MailFullsizePlace mailFullsizePlace) {
        this.mailFullsizePlace = mailFullsizePlace;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        initImageView();
        panel.setWidget(mailFullsizeView);
    }

    private void initImageView() {
        final ImageView imageView = mailFullsizeView.getImageView();
        if (mailFullsizePlace != null) {
            navigator.getPageAsync(
                    mailFullsizePlace.getTagId(),
                    mailFullsizePlace.getPhotoId(),
                    1,
                    new AsyncCallback<List<PhotoInfo>>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            mailFullsizeView.setStatusText("Image information could not be loaded due to a server error");
                        }

                        @Override
                        public void onSuccess(List<PhotoInfo> result) {
                            if (result.size() > 0) {
                                PhotoInfo info = result.get(0);
                                final ImageView.ImagePresenter imagePresenter = imagePresenterFactory.get(
                                        mailFullsizePlace.getTagId(),
                                        info,
                                        imageView,
                                        false);

                                scheduler.schedule(new Runnable() {
                                    @Override
                                    public void run() {
                                        imagePresenter.init();
                                    }
                                });

                            } else {
                                log.log(Level.FINE, "No photo info");
                            }
                        }
                    }
            );
        }
    }


    private void mailImage() {
        final String id = mailFullsizePlace.getPhotoId();
        mailFullsizeView.setStatusText("Requesting full size scheduling");
        dispatchAsync.execute(new RequestFullsizeImageAction(id),
                new AsyncCallback<FullsizeImageResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        mailFullsizeView.setStatusText("Full size failed on server due to a server error");
                    }

                    @Override
                    public void onSuccess(FullsizeImageResult result) {
                        mailFullsizeView.setStatusText(result.getMessage());
                    }
                });
    }

    @Override
    public void performAction(String actionId) {
        if (userActions.doMailFullsize.getId().equals(actionId)) {
            mailImage();
        }
    }

    @Override
    public MailFullsizeView.MailFullsizePresenter withPlace(MailFullsizePlace place) {
        setMailFullsizePlace(place);
        return this;
    }
}

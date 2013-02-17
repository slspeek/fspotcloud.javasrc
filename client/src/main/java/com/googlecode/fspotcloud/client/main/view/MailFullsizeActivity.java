package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.main.view.api.ImagePresenterFactory;
import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.client.main.view.api.MailFullsizeView;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.MailFullsizePlace;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.List;
import java.util.logging.Logger;

public class MailFullsizeActivity extends AbstractActivity implements MailFullsizeView.MailFullsizePresenter {

    private final Logger log = Logger.getLogger(MailFullsizeActivity.class.getName());
    private final MailFullsizeView mailFullsizeView;
    private final DispatchAsync dispatchAsync;
    private final ImagePresenterFactory imagePresenterFactory;
    private final MailFullsizePlace mailFullsizePlace;
    private final Navigator navigator;
    private final PlaceGoTo placeGoTo;
    private final IScheduler scheduler;

    @Inject

    public MailFullsizeActivity(
            @Assisted MailFullsizePlace mailFullsizePlace,
            MailFullsizeView mailFullsizeView,
            DispatchAsync dispatchAsync,
            ImagePresenterFactory imagePresenterFactory,
            Navigator navigator,
            PlaceGoTo placeGoTo, IScheduler scheduler) {
        this.mailFullsizeView = mailFullsizeView;
        this.dispatchAsync = dispatchAsync;
        this.imagePresenterFactory = imagePresenterFactory;
        this.mailFullsizePlace = mailFullsizePlace;
        this.navigator = navigator;
        this.placeGoTo = placeGoTo;
        this.scheduler = scheduler;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        initImageView();
        mailFullsizeView.setPresenter(this);
        panel.setWidget(mailFullsizeView);
    }

    private void initImageView() {
        final ImageView imageView = mailFullsizeView.getImageView();
        navigator.getPageAsync(
                mailFullsizePlace.getTagId(),
                mailFullsizePlace.getPhotoId(),
                1,
                new AsyncCallback<List<PhotoInfo>>() {
                    @Override
                    public void onFailure(Throwable caught) {

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
                            log.info("No photo info");
                        }
                    }
                }
        );
    }


    @Override
    public void mailImage() {
        final String id = mailFullsizePlace.getPhotoId();
        dispatchAsync.execute(new RequestFullsizeImageAction(id),
                new AsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        mailFullsizeView.setStatusText("Full size failed on server");
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        mailFullsizeView.setStatusText("Full size succeed on server, you can await the image. It may take a couple of days.");
                    }
                });
    }

    @Override
    public void cancel() {
        placeGoTo.goTo(new BasePlace(mailFullsizePlace.getTagId(), mailFullsizePlace.getPhotoId()));
    }
}

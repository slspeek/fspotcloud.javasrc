package com.googlecode.fspotcloud.client.useraction.raster.handler;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;

public class MailFullSizeHandler implements IActionHandler {
    private final Logger log = Logger.getLogger(MailFullSizeHandler.class.getName());
    private final PlaceWhere placeWhere;
    private final DispatchAsync dispatch;

    @Inject
    public MailFullSizeHandler(PlaceWhere placeWhere, DispatchAsync dispatch) {
        this.placeWhere = placeWhere;
        this.dispatch = dispatch;
    }

    @Override
    public void performAction(String actionId) {
        Place place = placeWhere.where();

        if (place instanceof BasePlace) {
            BasePlace basePlace = (BasePlace) place;
            final String id = basePlace.getPhotoId();
            dispatch.execute(new RequestFullsizeImageAction(id),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            log.info("Full size failed on server");
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            log.info("Full size succeed on server");
                        }
                    });
        }
    }
}

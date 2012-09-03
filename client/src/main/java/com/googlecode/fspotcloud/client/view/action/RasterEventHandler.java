/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.client.view.action;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.api.Initializable;
import com.googlecode.fspotcloud.client.main.event.UserEvent;
import com.googlecode.fspotcloud.client.main.event.raster.RasterEvent;
import com.googlecode.fspotcloud.client.main.event.raster.RasterType;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.RequestFullsizeImageAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Logger;


public class RasterEventHandler implements RasterEvent.Handler,
        Initializable {
    private final Logger log = Logger.getLogger(RasterEventHandler.class.getName());
    private final Navigator navigator;
    private final EventBus eventBus;
    private final DispatchAsync dispatch;
    private final PlaceWhere placeWhere;

    @Inject
    public RasterEventHandler(Navigator navigator, EventBus eventBus,
                              DispatchAsync dispatch, PlaceWhere placeWhere) {
        this.navigator = navigator;
        this.eventBus = eventBus;
        this.dispatch = dispatch;
        this.placeWhere = placeWhere;
    }

    @Override
    public void onEvent(UserEvent e) {
        switch ((RasterType) e.getActionDef()) {
            case ADD_COLUMN:
                navigator.increaseRasterWidth(1);

                break;

            case REMOVE_COLUMN:
                navigator.increaseRasterWidth(-1);

                break;

            case ADD_ROW:
                navigator.increaseRasterHeight(1);

                break;

            case REMOVE_ROW:
                navigator.increaseRasterHeight(-1);

                break;

            case SET_RASTER_2x2:
                navigator.setRasterDimension(2, 2);

                break;

            case SET_RASTER_3x3:
                navigator.setRasterDimension(3, 3);

                break;

            case SET_RASTER_4x4:
                navigator.setRasterDimension(4, 4);

                break;

            case SET_RASTER_5x5:
                navigator.setRasterDimension(5, 5);

                break;

            case TOGGLE_TABULAR_VIEW:
                navigator.toggleRasterView();

                break;

            case SET_DEFAULT_RASTER:
                navigator.resetRasterSize();

                break;

            case MAIL_FULLSIZE:
                mailFullsize();

                break;

            default:
                break;
        }
    }

    private void mailFullsize() {
        Place place = placeWhere.where();

        if (place instanceof BasePlace) {
            BasePlace basePlace = (BasePlace) place;
            String id = basePlace.getPhotoId();
            dispatch.execute(new RequestFullsizeImageAction(id),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            log.info("Fullsize failed on server");
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            log.info("Fullsize succeed on server");
                        }
                    });
        }
    }

    public void init() {
        eventBus.addHandler(RasterEvent.TYPE, this);
    }
}

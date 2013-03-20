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

package com.googlecode.fspotcloud.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PlaceGoToImpl implements IPlaceController {

    private final Logger log = Logger.getLogger(PlaceGoToImpl.class.getName());
    protected final PlaceController placeController;
    protected final MainPlaceHistoryMapper mainPlaceHistoryMapper;
    private BasePlace lastBasePlace = null;
    protected String activeTagId;

    @Inject
    public PlaceGoToImpl(PlaceController placeController,
                         MainPlaceHistoryMapper mainPlaceHistoryMapper) {
        this.placeController = placeController;
        this.mainPlaceHistoryMapper = mainPlaceHistoryMapper;
    }

    @Override
    public void goTo(Place place) {
        if (place instanceof DashboardPlace) {
            activeTagId = ((DashboardPlace) place).getTagId();
        } else if (place instanceof BasePlace) {
            activeTagId = ((BasePlace) place).getTagId();
            setLastBasePlace((BasePlace) place);
        }
        placeController.goTo(place);
    }

    @Override
    public void goTo(String token) {
        goTo(mainPlaceHistoryMapper.getPlace(token.substring(1)));
    }

    public String getLastTagId() {
        return activeTagId;
    }

    protected BasePlace getLastBasePlace() {
        log.log(Level.FINEST, "returning last base place:" + lastBasePlace);
        return lastBasePlace;
    }

    protected void setLastBasePlace(BasePlace lastBasePlace) {
        log.log(Level.FINEST, "last base place set to " + lastBasePlace);
        this.lastBasePlace = lastBasePlace;
    }


    @Override
    public BasePlace where() {
        Place place = placeController.getWhere();
        //FIXME: We should listen to PlaceController events
        if (place instanceof BasePlace) {
            setLastBasePlace((BasePlace) place);
        }

        return getLastBasePlace();
    }

    @Override
    public String whereToken() {
        return "#" + mainPlaceHistoryMapper.getToken(getRawWhere());
    }

    @Override
    public Place getRawWhere() {
        Place place = placeController.getWhere();
        return place;
    }
}

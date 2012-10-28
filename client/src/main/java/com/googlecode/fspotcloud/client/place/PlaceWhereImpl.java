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
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;


public class PlaceWhereImpl extends PlaceGoToImpl implements PlaceWhere {
    private BasePlace lastBasePlace = new BasePlace("latest", "");

    @Inject
    public PlaceWhereImpl(PlaceController placeController) {
        super(placeController);
    }

    @Override
    public BasePlace where() {
        Place place = placeController.getWhere();

        if (place instanceof BasePlace) {
            lastBasePlace = (BasePlace) place;
        }

        return lastBasePlace;
    }
}

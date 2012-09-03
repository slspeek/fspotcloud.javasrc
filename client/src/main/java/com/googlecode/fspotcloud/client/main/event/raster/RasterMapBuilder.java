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

package com.googlecode.fspotcloud.client.main.event.raster;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.event.AbstractActionMap;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;
import com.googlecode.fspotcloud.client.view.action.api.UserActionFactory;


public class RasterMapBuilder extends AbstractActionMap {
    private Resources resources;

    @Inject
    public RasterMapBuilder(UserActionFactory userActionFactory,
                            Resources resources) {
        super(userActionFactory, "Raster");
        this.resources = resources;
    }

    @Override
    public void buildMap() {
        put(RasterType.ADD_COLUMN, resources.addColumnIcon());
        put(RasterType.ADD_ROW, resources.addRowIcon());
        put(RasterType.REMOVE_COLUMN, resources.removeColumnIcon());
        put(RasterType.REMOVE_ROW, resources.removeRowIcon());
        put(RasterType.SET_DEFAULT_RASTER, resources.resetIcon());
        put(RasterType.SET_RASTER_2x2, resources.icon2x2());
        put(RasterType.SET_RASTER_3x3, resources.icon3x3());
        put(RasterType.SET_RASTER_4x4, resources.icon4x4());
        put(RasterType.SET_RASTER_5x5, resources.icon5x5());
        put(RasterType.TOGGLE_TABULAR_VIEW, resources.tabularIcon());
        put(RasterType.MAIL_FULLSIZE, resources.emailIcon());
    }

    private void put(ActionDef actionDef, ImageResource icon) {
        put(actionDef, icon, new RasterEventProvider(actionDef));
    }
}

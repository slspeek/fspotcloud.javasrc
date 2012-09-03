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

import com.google.gwt.event.dom.client.KeyCodes;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;

public enum RasterType implements ActionDef {
    ADD_COLUMN("add-colum", "Add column", "Adds one column to raster",
            new KeyStroke('C'), null),
    ADD_ROW("add-row", "Add row", "Adds one row to raster", new KeyStroke('R'),
            null),
    REMOVE_COLUMN("remove-column", "Remove column",
            "Removes one column from the raster", new KeyStroke('X'), null),
    REMOVE_ROW("remove-row", "Remove row", "Removes one row from the raster",
            new KeyStroke('E'), null),
    SET_DEFAULT_RASTER("reset", "Reset raster", "Resets raster to defaults",
            new KeyStroke('0'), null),
    TOGGLE_TABULAR_VIEW("raster", "Toggle raster", "Toggle tabular viewing",
            new KeyStroke(KeyCodes.KEY_SHIFT), new KeyStroke((int) '1')),
    SET_RASTER_2x2("2x2", "2x2", "Sets the raster to 2 x 2",
            new KeyStroke('2'), null),
    SET_RASTER_3x3("3x3", "3x3", "Sets the raster to 3 x 3",
            new KeyStroke('3'), null),
    SET_RASTER_4x4("4x4", "4x4", "Sets the raster to 4 x 4",
            new KeyStroke('4'), null),
    SET_RASTER_5x5("5x5", "5x5", "Sets the raster to 5 x 5",
            new KeyStroke('5'), null),
    MAIL_FULLSIZE("mail-fullsize", "Mail fullsize",
            "Mail the high resolution version of the image to you",
            new KeyStroke('8'), null);
    private final KeyStroke key;
    private final KeyStroke alternateKey;
    private final String caption;
    private final String id;
    private final String description;

    private RasterType(String id, String caption, String description,
                       KeyStroke key, KeyStroke alternateKey) {
        this.key = key;
        this.alternateKey = alternateKey;
        this.caption = caption;
        this.id = id;
        this.description = description;
    }

    @Override
    public KeyStroke getAlternateKey() {
        return alternateKey;
    }

    @Override
    public KeyStroke getKey() {
        return key;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getId() {
        return id;
    }
}

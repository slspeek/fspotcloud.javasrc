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

import com.googlecode.fspotcloud.client.place.api.Raster;
import com.googlecode.fspotcloud.client.place.api.RasterSetter;


public class RasterState implements Raster, RasterSetter {
    private int width;
    private int height;

    @Override
    public void setColumnCount(int width) {
        this.width = width;
    }

    @Override
    public void setRowCount(int height) {
        this.height = height;
    }

    @Override
    public int getRowCount() {
        return height;
    }

    @Override
    public int getColumnCount() {
        return width;
    }
}

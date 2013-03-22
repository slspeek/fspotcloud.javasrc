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

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.RasterHeight;
import com.googlecode.fspotcloud.client.main.gin.RasterWidth;
import com.googlecode.fspotcloud.client.place.api.Raster;
import com.googlecode.fspotcloud.client.place.api.RasterAware;


public class RasterState implements Raster, RasterAware {

    public final int rasterWidth;
    public final int rasterHeight;
    private int width;
    private int height;
    private boolean autoHide;

    @Inject
    public RasterState(@RasterWidth int rasterWidth,
                       @RasterHeight int rasterHeight) {
        this.rasterWidth = rasterWidth;
        this.rasterHeight = rasterHeight;
        this.width = rasterWidth;
        this.height = rasterHeight;
    }

    @Override
    public void setColumnCount(int width) {
        if (this.height * width > 1) {
            this.width = width;
        }
    }

    @Override
    public void setRowCount(int height) {
        if (this.width * height > 1) {
            this.height = height;
        }
    }

    @Override
    public void reset() {
        setColumnCount(rasterWidth);
        setRowCount(rasterHeight);
    }

    @Override
    public void increaseColumnCount(int amount) {
        setColumnCount(getColumnCount() + amount);
    }

    @Override
    public void increaseRowCount(int amount) {
        setRowCount(getRowCount() + amount);
    }

    @Override
    public int getRowCount() {
        return height;
    }

    @Override
    public int getColumnCount() {
        return width;
    }

    public void setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
    }

    public boolean isAutoHide() {
        return autoHide;
    }
}

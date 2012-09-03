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

package com.googlecode.fspotcloud.shared.peer;

import com.openpojo.business.annotation.BusinessKey;

import java.io.Serializable;


public class ImageSpecs extends BusinessBase implements Serializable {
    private static final long serialVersionUID = 5812879917430846998L;
    @BusinessKey
    private final int width;
    @BusinessKey
    private final int height;
    @BusinessKey
    private final int thumbWidth;
    @BusinessKey
    private final int thumbHeight;

    public ImageSpecs(int width, int height, int thumbWidth, int thumbHeight) {
        super();
        this.width = width;
        this.height = height;
        this.thumbWidth = thumbWidth;
        this.thumbHeight = thumbHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getThumbWidth() {
        return thumbWidth;
    }

    public int getThumbHeight() {
        return thumbHeight;
    }
}

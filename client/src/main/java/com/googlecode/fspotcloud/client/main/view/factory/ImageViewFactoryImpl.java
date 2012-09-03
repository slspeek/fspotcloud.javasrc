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

package com.googlecode.fspotcloud.client.main.view.factory;

import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.client.main.view.api.ImageViewFactory;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;


public class ImageViewFactoryImpl implements ImageViewFactory {
    private Map<String, ImageView> imageViewCache = new HashMap<String, ImageView>();
    ImageViewFactory factory;

    @Inject
    public ImageViewFactoryImpl(ImageViewFactory factory) {
        super();
        this.factory = factory;
    }

    @Override
    public ImageView get(String location) {
        ImageView view = imageViewCache.get(location);

        if (view == null) {
            view = factory.get(location);
            imageViewCache.put(location, view);
        }

        return view;
    }
}

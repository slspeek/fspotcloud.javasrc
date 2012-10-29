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

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.TagViewImpl;
import com.googlecode.fspotcloud.client.main.view.TagActivity;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterPresenterFactory;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterView;
import com.googlecode.fspotcloud.client.main.view.api.TagPresenterFactory;
import com.googlecode.fspotcloud.client.main.view.api.TagView;
import com.googlecode.fspotcloud.client.main.view.api.TagView.TagPresenter;
import com.googlecode.fspotcloud.client.main.view.api.TreeView.TreePresenter;
import com.googlecode.fspotcloud.client.place.BasePlace;

import java.util.logging.Logger;


public class TagPresenterFactoryImpl implements TagPresenterFactory {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(TagPresenterFactoryImpl.class.getName());
    private final TagViewImpl tagView;
    private final TreePresenter treePresenter;
    private final ImageRasterPresenterFactory rasterFactory;

    @Inject
    public TagPresenterFactoryImpl(TagView tagView,
                                   TreePresenter treePresenter,
                                   ImageRasterPresenterFactory rasterFactory) {
        super();
        this.tagView = (TagViewImpl) tagView;
        this.treePresenter = treePresenter;
        this.rasterFactory = rasterFactory;
        init();
    }

    private void init() {
        treePresenter.init();
    }

    @Override
    public TagPresenter get(BasePlace place) {
        //SingleImagePresenter integration should go here
        final ImageRasterView.ImageRasterPresenter rasterPresenter = rasterFactory.get(place,
                tagView.getImageRasterView());
        TagPresenter presenter = new TagActivity(tagView, rasterPresenter);
        treePresenter.setPlace(place);
        return presenter;
    }
}

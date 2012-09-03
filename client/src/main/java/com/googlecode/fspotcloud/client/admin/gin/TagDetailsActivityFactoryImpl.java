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

package com.googlecode.fspotcloud.client.admin.gin;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.view.TagDetailsActivity;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsActivityFactory;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsView;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsView.TagDetailsPresenter;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import net.customware.gwt.dispatch.client.DispatchAsync;


public class TagDetailsActivityFactoryImpl implements TagDetailsActivityFactory {
    @Inject
    private TagDetailsView tagDetailsView;
    @Inject
    private DataManager dataManager;
    @Inject
    private DispatchAsync dispatch;
    @Inject
    private PlaceGoTo placeGoTo;

    @Override
    public TagDetailsPresenter get(TagPlace place) {
        TagDetailsPresenter presenter = new TagDetailsActivity(tagDetailsView,
                place, dataManager, dispatch, placeGoTo);
        presenter.init();

        return presenter;
    }
}

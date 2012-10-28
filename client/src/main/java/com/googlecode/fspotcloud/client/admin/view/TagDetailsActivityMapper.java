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

package com.googlecode.fspotcloud.client.admin.view;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsActivityFactory;
import com.googlecode.fspotcloud.client.admin.view.api.TagDetailsView;
import com.googlecode.fspotcloud.client.place.TagPlace;

import java.util.logging.Logger;


public class TagDetailsActivityMapper implements ActivityMapper {
    private final Logger log = Logger.getLogger(TagDetailsActivityMapper.class.getName());
    private final TagDetailsActivityFactory tagDetailsActivityFactory;

    @Inject
    public TagDetailsActivityMapper(
            TagDetailsActivityFactory tagDetailsActivityFactory) {
        super();
        this.tagDetailsActivityFactory = tagDetailsActivityFactory;
    }

    @Override
    public Activity getActivity(Place place) {
        log.info("getActivity: " + place);

        TagDetailsView.TagDetailsPresenter activity = null;

        if (place instanceof TagPlace) {
            activity = tagDetailsActivityFactory.get((TagPlace) place);
            activity.init();
        }

        return activity;
    }
}

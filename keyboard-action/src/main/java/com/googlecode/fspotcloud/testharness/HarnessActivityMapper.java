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

package com.googlecode.fspotcloud.testharness;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;

import java.util.logging.Level;
import java.util.logging.Logger;


public class HarnessActivityMapper implements ActivityMapper {
    private final Logger log = Logger.getLogger(HarnessActivityMapper.class.getName());

    private final HomeView.HomePresenter homePresenter;
    private final OutView.OutPresenter outPresenter;

    @Inject
    public HarnessActivityMapper(HomeView.HomePresenter homePresenter, OutView.OutPresenter outPresenter) {
        super();

        this.homePresenter = homePresenter;
        this.outPresenter = outPresenter;
    }

    @Override
    public Activity getActivity(Place place) {
        log.log(Level.FINE, "getActivity : " + place);
        if (place instanceof HomePlace) {
            return homePresenter;
        } else {
            return outPresenter;
        }
    }
}

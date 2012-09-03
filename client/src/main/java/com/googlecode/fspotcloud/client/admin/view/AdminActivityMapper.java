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
import com.googlecode.fspotcloud.client.admin.view.api.DashboardView;
import com.googlecode.fspotcloud.client.admin.view.api.TagApprovalView;
import com.googlecode.fspotcloud.client.place.TagApprovalPlace;
import com.googlecode.fspotcloud.client.place.TagPlace;

import javax.inject.Inject;


public class AdminActivityMapper implements ActivityMapper {
    @Inject
    private TagApprovalView.TagApprovalPresenter approvalPresenter;
    @Inject
    private DashboardView.DashboardPresenter dashboardPresenter;

    @Override
    public Activity getActivity(Place place) {
        if (place instanceof TagPlace) {
            return dashboardPresenter;
        } else if (place instanceof TagApprovalPlace) {
            approvalPresenter.setTagId(((TagApprovalPlace) place).getTagId());

            return approvalPresenter;
        }

        return null; //To change body of implemented methods use File | Settings | File Templates.
    }
}

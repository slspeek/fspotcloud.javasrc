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

package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

import java.util.logging.Level;
import java.util.logging.Logger;


public class MainWindowActivityMapper implements ActivityMapper {
    private final Logger log = Logger.getLogger(MainWindowActivityMapper.class.getName());
    private final TagPresenterFactory tagPresenterFactory;
    private final SlideshowActivityFactory slideshowActivityFactory;
    private final Navigator navigator;
    private final IModeController modeController;
    private final LoginView.LoginPresenter loginPresenter;
    private final SignUpView.SignUpPresenter signUpPresenter;
    private final UserAccountView.UserAccountPresenter userAccountActivity;
    private final MyUserGroupsView.MyUserGroupsPresenter myUserGroupsPresenter;
    private final EditUserGroupView.EditUserGroupPresenter editUserGroupPresenter;
    private final ManageUsersView.ManageUsersPresenter manageUsersPresenter;

    @Inject
    public MainWindowActivityMapper(TagPresenterFactory tagPresenterFactory,
                                    SlideshowActivityFactory slideshowActivityFactory,
                                    Navigator navigator, IModeController modeController,
                                    LoginView.LoginPresenter loginPresenter,
                                    SignUpView.SignUpPresenter signUpPresenter,
                                    UserAccountView.UserAccountPresenter userAccountActivity,
                                    MyUserGroupsView.MyUserGroupsPresenter myUserGroupsPresenter,
                                    EditUserGroupView.EditUserGroupPresenter editUserGroupPresenter,
                                    ManageUsersView.ManageUsersPresenter manageUsersPresenter) {
        super();
        this.slideshowActivityFactory = slideshowActivityFactory;
        this.tagPresenterFactory = tagPresenterFactory;
        this.navigator = navigator;
        this.modeController = modeController;
        this.loginPresenter = loginPresenter;
        this.signUpPresenter = signUpPresenter;
        this.userAccountActivity = userAccountActivity;
        this.myUserGroupsPresenter = myUserGroupsPresenter;
        this.editUserGroupPresenter = editUserGroupPresenter;
        this.manageUsersPresenter = manageUsersPresenter;
    }

    @Override
    public Activity getActivity(Place place) {
        log.log(Level.FINE, "getActivity : " + place);
        storeCurrentRasterDimension(place);

        Activity activity = null;

        if (place instanceof UserAccountPlace) {
            activity = userAccountActivity;
            modeController.setMode(Modes.LOGIN);
        } else if (place instanceof SignUpPlace) {
            activity = signUpPresenter;
            modeController.setMode(Modes.LOGIN);
        } else if (place instanceof ManageUsersPlace) {
            activity = manageUsersPresenter;
            manageUsersPresenter.setId(((ManageUsersPlace) place).getUserGroupId());
            modeController.setMode(Modes.LOGIN);
        } else if (place instanceof MyUserGroupsPlace) {
            activity = myUserGroupsPresenter;
            modeController.setMode(Modes.LOGIN);
        } else if (place instanceof EditUserGroupPlace) {
            activity = editUserGroupPresenter;
            editUserGroupPresenter.setId(((EditUserGroupPlace) place).getUserGroupId());
            modeController.setMode(Modes.LOGIN);
        } else if (place instanceof LoginPlace) {
            activity = loginPresenter;
            modeController.setMode(Modes.LOGIN);
        } else if (place instanceof SlideshowPlace) {
            SlideshowPlace slideshowPlace = (SlideshowPlace) place;
            activity = slideshowActivityFactory.get(slideshowPlace);
            modeController.setMode(Modes.SLIDESHOW);
        } else if (place instanceof BasePlace) {
            BasePlace basePlace = (BasePlace) place;

            if (basePlace.getTagId().equals("latest")) {
                navigator.goToLatestTag();
            }

            activity = tagPresenterFactory.get(basePlace);
            modeController.setMode(Modes.TAG_VIEW);
        } else {
            log.warning("getActivity will return null for:" + place);
        }

        return activity;
    }

    private void storeCurrentRasterDimension(Place place) {
        if (place instanceof BasePlace) {
            BasePlace basePlace = (BasePlace) place;
            int width = basePlace.getColumnCount();
            int height = basePlace.getRowCount();

            if ((height * width) > 1) {
                navigator.setRasterWidth(width);
                navigator.setRasterHeight(height);
            }
        }
    }
}

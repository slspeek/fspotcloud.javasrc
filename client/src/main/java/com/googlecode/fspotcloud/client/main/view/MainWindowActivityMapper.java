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

import com.google.code.ginmvp.client.ActivityAsyncProxy;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.place.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindowActivityMapper implements ActivityMapper {
	private final Logger log = Logger.getLogger(MainWindowActivityMapper.class
			.getName());
	private final TagActivityFactory tagActivityFactory;
	private final SlideshowActivityFactory slideshowActivityFactory;
	private final RasterState rasterState;
	private final MailFullsizeView.MailFullsizePresenter mailFullsizeActivity;
	private final ActivityAsyncProxy<SendConfirmationView.SendConfirmationPresenter> confirmationPresenterActivity;
	private final ActivityAsyncProxy<LoginView.LoginPresenter> loginPresenter;
	private final ActivityAsyncProxy<SignUpView.SignUpPresenter> signUpPresenter;
	private final ActivityAsyncProxy<UserAccountView.UserAccountPresenter> userAccountActivity;
	private final ActivityAsyncProxy<ManageGroupsView.ManageGroupsPresenter> myUserGroupsPresenter;
	private final EditUserGroupView.EditUserGroupPresenter editUserGroupPresenter;
	private final ManageUsersView.ManageUsersPresenter manageUsersPresenter;
	private final ActivityAsyncProxy<EmailConfirmationView.EmailConfirmationPresenter> emailConfirmationPresenter;
	private final ChangePasswordView.ChangePasswordPresenter changePasswordPresenter;
	private final ActivityAsyncProxy<SendPasswordResetView.SendPasswordResetPresenter> sendResetPasswordPresenter;
	private final HomeView.HomePresenter homePresenter;
	private final TagApprovalView.TagApprovalPresenter approvalPresenter;
	private final DashboardView.DashboardPresenter dashboardPresenter;

	@Inject
	public MainWindowActivityMapper(
			TagActivityFactory tagActivityFactory,
			SlideshowActivityFactory slideshowActivityFactory,
			RasterState rasterState,
			MailFullsizeView.MailFullsizePresenter mailFullsizeActivity,
			ActivityAsyncProxy<SendConfirmationView.SendConfirmationPresenter> confirmationPresenterActivity,
			ActivityAsyncProxy<LoginView.LoginPresenter> loginPresenter,
			ActivityAsyncProxy<SignUpView.SignUpPresenter> signUpPresenter,
			ActivityAsyncProxy<UserAccountView.UserAccountPresenter> userAccountActivity,
			ActivityAsyncProxy<ManageGroupsView.ManageGroupsPresenter> myUserGroupsPresenter,
			EditUserGroupView.EditUserGroupPresenter editUserGroupPresenter,
			ManageUsersView.ManageUsersPresenter manageUsersPresenter,
			ActivityAsyncProxy<EmailConfirmationView.EmailConfirmationPresenter> emailConfirmationPresenter,
			ChangePasswordView.ChangePasswordPresenter changePasswordPresenter,
			ActivityAsyncProxy<SendPasswordResetView.SendPasswordResetPresenter> sendResetPasswordPresenter,
			HomeView.HomePresenter homePresenter,
			TagApprovalView.TagApprovalPresenter approvalPresenter,
			DashboardView.DashboardPresenter dashboardPresenter) {
		super();
		this.slideshowActivityFactory = slideshowActivityFactory;
		this.tagActivityFactory = tagActivityFactory;
		this.rasterState = rasterState;
		this.mailFullsizeActivity = mailFullsizeActivity;
		this.confirmationPresenterActivity = confirmationPresenterActivity;
		this.loginPresenter = loginPresenter;
		this.signUpPresenter = signUpPresenter;
		this.userAccountActivity = userAccountActivity;
		this.myUserGroupsPresenter = myUserGroupsPresenter;
		this.editUserGroupPresenter = editUserGroupPresenter;
		this.manageUsersPresenter = manageUsersPresenter;
		this.emailConfirmationPresenter = emailConfirmationPresenter;
		this.changePasswordPresenter = changePasswordPresenter;
		this.sendResetPasswordPresenter = sendResetPasswordPresenter;
		this.homePresenter = homePresenter;
		this.approvalPresenter = approvalPresenter;
		this.dashboardPresenter = dashboardPresenter;
	}

	@Override
	public Activity getActivity(Place place) {
		log.log(Level.FINE, "getActivity : " + place);
		storeCurrentRasterDimension(place);

		Activity activity = null;
		if (place instanceof TagApprovalPlace) {
			approvalPresenter.setTagId(((TagApprovalPlace) place).getTagId());
			return approvalPresenter;
		} else if (place instanceof MailFullsizePlace) {
			activity = mailFullsizeActivity
					.withPlace((MailFullsizePlace) place);
		} else if (place instanceof SendConfirmationPlace) {
			activity = confirmationPresenterActivity;
		} else if (place instanceof HomePlace) {
			activity = homePresenter;
		} else if (place instanceof ChangePasswordPlace) {
			activity = changePasswordPresenter
					.withPlace((ChangePasswordPlace) place);
		} else if (place instanceof SendPasswordResetPlace) {
			activity = sendResetPasswordPresenter;
		} else if (place instanceof UserAccountPlace) {
			activity = userAccountActivity;
		} else if (place instanceof EmailConfirmationPlace) {
			activity = emailConfirmationPresenter;
		} else if (place instanceof SignUpPlace) {
			activity = signUpPresenter;
		} else if (place instanceof ManageUsersPlace) {
			activity = manageUsersPresenter;
			manageUsersPresenter.setId(((ManageUsersPlace) place)
					.getUserGroupId());
		} else if (place instanceof ManageGroupsPlace) {
			activity = myUserGroupsPresenter;
		} else if (place instanceof EditUserGroupPlace) {
			activity = editUserGroupPresenter;
			editUserGroupPresenter.setId(((EditUserGroupPlace) place)
					.getUserGroupId());
		} else if (place instanceof LoginPlace) {
			activity = loginPresenter;
		} else if (place instanceof SlideshowPlace) {
			SlideshowPlace slideshowPlace = (SlideshowPlace) place;
			activity = slideshowActivityFactory.get(slideshowPlace);
		} else if (place instanceof BasePlace) {
			BasePlace basePlace = (BasePlace) place;
			activity = tagActivityFactory.get(basePlace);
		} else if (place instanceof DashboardPlace) {
			return dashboardPresenter.withPlace((DashboardPlace) place);
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
				rasterState.setColumnCount(width);
				rasterState.setRowCount(height);
			}
			rasterState.setAutoHide(basePlace.isAutoHide());
		}
	}
}

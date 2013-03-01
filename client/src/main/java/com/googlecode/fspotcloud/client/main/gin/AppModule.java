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

package com.googlecode.fspotcloud.client.main.gin;

import com.google.code.ginmvp.client.GinMvpModule;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.data.DataManagerImpl;
import com.googlecode.fspotcloud.client.enduseraction.EndUserActionFactory;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.MVPSetup;
import com.googlecode.fspotcloud.client.main.ui.*;
import com.googlecode.fspotcloud.client.main.view.*;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.main.view.factory.SingleImageViewProvider;
import com.googlecode.fspotcloud.client.main.view.factory.SlideshowActivityFactoryImpl;
import com.googlecode.fspotcloud.client.main.view.factory.SlideshowDelayPresenterFactoryImpl;
import com.googlecode.fspotcloud.client.main.view.factory.TagActivityFactoryImpl;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Slideshow;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.UIRegistrationBuilder;

public class AppModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(ReplaceLocation.class).to(ReplaceLocationImpl.class);
        bind(StatusView.class).to(StatusViewImpl.class);
        bind(StatusView.class).annotatedWith(Dashboard.class).to(StatusViewImpl.class).in(Singleton.class);
        bind(StatusView.class).annotatedWith(ManageGroups.class).to(StatusViewImpl.class).in(Singleton.class);
        bind(StatusView.class).annotatedWith(ManageUsers.class).to(StatusViewImpl.class).in(Singleton.class);
        bind(UIRegistrationBuilder.class).to(EndUserActionFactory.class);
        bind(IScheduler.class).to(SchedulerImpl.class);
        install(new GinMvpModule(MainWindowActivityMapper.class, HomePlace.class, MvpDisplay.class, MainPlaceHistoryMapper.class));
        bind(HomeView.class).to(HomeViewImpl.class);
        bind(HomeView.HomePresenter.class).to(HomeActivity.class);
        bind(ImageView.class).annotatedWith(SingleImageView.class).toProvider(SingleImageViewProvider.class).in(Singleton.class);
        bind(DoubleImageView.class).to(DoubleImageViewImpl.class).in(Singleton.class);
        bind(MainWindowActivityMapper.class).in(Singleton.class);
        bind(SendConfirmationView.class).to(SendConfirmationViewImpl.class).in(Singleton.class);
        bind(SendConfirmationView.SendConfirmationPresenter.class).to(SendConfirmationActivity.class).in(Singleton.class);
        bind(MailFullsizeView.class).to(MailFullsizeViewImpl.class).in(Singleton.class);
        bind(MailFullsizeView.MailFullsizePresenter.class).to(MailFullsizeActivity.class).in(Singleton.class);
        bind(ChangePasswordView.class).to(ChangePasswordViewImpl.class).in(Singleton.class);
        bind(ChangePasswordView.ChangePasswordPresenter.class).to(ChangePasswordActivity.class).in(Singleton.class);
        bind(SendPasswordResetView.class).to(SendPasswordResetViewImpl.class).in(Singleton.class);
        bind(SendPasswordResetView.SendPasswordResetPresenter.class).to(SendPasswordResetActivity.class).in(Singleton.class);
        bind(DataManager.class).to(DataManagerImpl.class).in(Singleton.class);
        bind(ActionButton.class).annotatedWith(BigToPhotos.class).toProvider(BigToPhotosButtonProvider.class);
        bind(MVPSetup.class).in(Singleton.class);
        bind(TagCell.class);
        bind(TagView.class).to(TagViewImpl.class).in(Singleton.class);

        bind(ImageRasterView.class).to(ImageRasterViewImpl.class);
        bind(SlideshowActivityFactory.class)
                .to(SlideshowActivityFactoryImpl.class);
        bind(SlideshowView.class).to(SlideshowViewImpl.class);
        bind(LoginView.class).to(LoginViewImpl.class).in(Singleton.class);
        bind(LoginView.LoginPresenter.class).to(LoginPresenterImpl.class).in(Singleton.class);
        bind(SignUpView.class).to(SignUpViewImpl.class).in(Singleton.class);
        bind(SignUpView.SignUpPresenter.class).to(SignUpActivity.class).in(Singleton.class);
        bind(UserAccountView.class).to(UserAccountViewImpl.class);
        bind(UserAccountView.UserAccountPresenter.class)
                .to(UserAccountActivity.class);
        bind(ManageGroupsView.class).to(ManageGroupsViewImpl.class).in(Singleton.class);
        bind(ManageGroupsView.ManageGroupsPresenter.class)
                .to(ManageGroupsActivity.class).in(Singleton.class);
        bind(EditUserGroupView.class).to(EditUserGroupViewImpl.class).in(Singleton.class);
        bind(EditUserGroupView.EditUserGroupPresenter.class)
                .to(EditUserGroupActivity.class).in(Singleton.class);
        bind(ManageUsersView.class).to(ManageUsersViewImpl.class).in(Singleton.class);
        bind(ManageUsersView.ManageUsersPresenter.class)
                .to(ManageUsersActivity.class).in(Singleton.class);
        bind(EmailConfirmationView.class).to(EmailConfirmationViewImpl.class);
        bind(EmailConfirmationView.EmailConfirmationPresenter.class)
                .to(EmailConfirmationActivity.class);
        bind(PlaceCalculator.class).in(Singleton.class);
        bind(IPlaceController.class).to(PlaceGoToImpl.class).in(Singleton.class);
        bind(PlaceControllerProvider.class).in(Singleton.class);
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(SlideshowDelayView.class).to(SlideshowDelayViewImpl.class).in(Singleton.class);
        bind(TimerInterface.class).to(TimerImpl.class);
        bind(Navigator.class).to(NavigatorImpl.class).in(Singleton.class);
        bind(Slideshow.class).to(SlideshowImpl.class).in(Singleton.class);
        bind(TagActivityFactory.class).to(TagActivityFactoryImpl.class);

        bind(SlideshowDelayView.SlideshowPresenter.class)
                .toProvider(SlideshowDelayPresenterFactoryImpl.class);
        install(new GinFactoryModuleBuilder().implement(
                ImageRasterView.ImageRasterPresenter.class,
                ImageRasterPresenterImpl.class)
                .build(ImageRasterPresenterFactory.class));
        install(new GinFactoryModuleBuilder().implement(
                ImageView.ImagePresenter.class,
                ImagePresenterImpl.class).build(ImagePresenterFactory.class));
        install(new GinFactoryModuleBuilder().implement(ImageView.class,
                ImageViewImpl.class).build(ImageViewFactory.class));

        bind(ITreeSelectionHandler.class).annotatedWith(AdminTreeView.class).to(AdminTreeSelectionHandler.class);
        bind(ITreeSelectionHandler.class).annotatedWith(BasicTreeView.class).to(TreeSelectionHandler.class);
        bind(OpenNewTab.class).to(OpenNewTabImpl.class);

        //admin
        bind(TagDetailsView.TagDetailsPresenter.class)
                .to(TagDetailsActivity.class).in(Singleton.class);
        bind(TagDetailsView.class).to(TagDetailsViewImpl.class)
                .in(Singleton.class);
        bind(DashboardView.class).to(DashboardViewImpl.class).in(Singleton.class);
        bind(DashboardView.DashboardPresenter.class)
                .to(DashboardActivity.class);
        bind(PeerActionsView.class).to(PeerActionsViewImpl.class)
                .in(Singleton.class);
        bind(PeerActionsView.PeerActionsPresenter.class).to(PeerActionsPresenter.class);
        bind(TreeView.TreePresenter.class).annotatedWith(AdminTreeView.class).to(AdminTreePresenterImpl.class)
                .in(Singleton.class);
        bind(TreeView.class).annotatedWith(AdminTreeView.class).toProvider(AdminTreeViewProvider.class).in(Singleton.class);
        bind(TreeView.class).annotatedWith(BasicTreeView.class).toProvider(BasicTreeViewProvider.class).in(Singleton.class);
        bind(TreeView.TreePresenter.class).annotatedWith(BasicTreeView.class).to(TreePresenterImpl.class)
                .in(Singleton.class);

        bind(TagApprovalView.class).to(TagApprovalViewImpl.class).in(Singleton.class);
        ;
        bind(TagApprovalView.TagApprovalPresenter.class)
                .to(TagApprovalActivity.class).in(Singleton.class);

        bind(IClientLoginManager.class).to(ClientLoginManager.class).in(Singleton.class);
    }
}

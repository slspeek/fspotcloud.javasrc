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
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.data.DataManagerImpl;
import com.googlecode.fspotcloud.client.data.GetTagTreeMemoProc;
import com.googlecode.fspotcloud.client.main.MVPSetup;
import com.googlecode.fspotcloud.client.main.ui.*;
import com.googlecode.fspotcloud.client.main.view.*;
import com.googlecode.fspotcloud.client.main.view.api.*;
import com.googlecode.fspotcloud.client.main.view.factory.SingleImageViewProvider;
import com.googlecode.fspotcloud.client.main.view.factory.SlideshowActivityFactoryImpl;
import com.googlecode.fspotcloud.client.main.view.factory.SlideshowDelayPresenterFactoryImpl;
import com.googlecode.fspotcloud.client.main.view.factory.TagPresenterFactoryImpl;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.client.place.api.Slideshow;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;

public class AppModule extends AbstractGinModule {

    @Override
    protected void configure() {
        install(new GinMvpModule(MainWindowActivityMapper.class,  BasePlace.class,MvpDisplay.class, MainPlaceHistoryMapper.class));
        bind(ImageView.class).annotatedWith(SingleImageView.class).toProvider(SingleImageViewProvider.class).in(Singleton.class);
        bind(DoubleImageView.class).to(DoubleImageViewImpl.class).in(Singleton.class);
        bind(MainWindowActivityMapper.class).in(Singleton.class);
        //bind(GetTagTreeMemoProc.class);
        bind(DataManager.class).to(DataManagerImpl.class).in(Singleton.class);
        bind(MVPSetup.class).in(Singleton.class);
        bind(TagCell.class);
        bind(TagView.class).to(TagViewImpl.class).in(Singleton.class);
        bind(TreeView.class).to(TreeViewImpl.class).in(Singleton.class);
        bind(ImageRasterView.class).to(ImageRasterViewImpl.class);
        bind(SlideshowActivityFactory.class)
                .to(SlideshowActivityFactoryImpl.class);
        bind(SlideshowView.class).to(SlideshowViewImpl.class);
        bind(LoginView.class).to(LoginViewImpl.class);
        bind(LoginView.LoginPresenter.class).to(LoginPresenterImpl.class);
        bind(SignUpView.class).to(SignUpViewImpl.class);
        bind(SignUpView.SignUpPresenter.class).to(SignUpPresenterImpl.class);
        bind(UserAccountView.class).to(UserAccountViewImpl.class);
        bind(UserAccountView.UserAccountPresenter.class)
                .to(UserAccountPresenterImpl.class);
        bind(MyUserGroupsView.class).to(MyUserGroupsViewImpl.class);
        bind(MyUserGroupsView.MyUserGroupsPresenter.class)
                .to(MyUserGroupsPresenterImpl.class);
        bind(EditUserGroupView.class).to(EditUserGroupViewImpl.class);
        bind(EditUserGroupView.EditUserGroupPresenter.class)
                .to(EditUserGroupPresenterImpl.class);
        bind(ManageUsersView.class).to(ManageUsersViewImpl.class);
        bind(ManageUsersView.ManageUsersPresenter.class)
                .to(ManageUsersPresenterImpl.class);
        bind(EmailConfirmationView.class).to(EmailConfirmationViewImpl.class);
        bind(EmailConfirmationView.EmailConfirmationPresenter.class).to(EmailConfirmationActivity.class);

        bind(PlaceCalculator.class);
        bind(PlaceGoTo.class).to(PlaceGoToImpl.class);
        bind(PlaceWhere.class).to(PlaceWhereImpl.class);
        //bind(PlaceController.class).toProvider(PlaceControllerProvider.class);
        bind(PlaceControllerProvider.class).in(Singleton.class);
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(SlideshowDelayView.class).to(SlideshowDelayViewImpl.class).in(Singleton.class);
        bind(TimerInterface.class).to(TimerImpl.class);
        bind(Navigator.class).to(NavigatorImpl.class).in(Singleton.class);
        bind(Slideshow.class).to(SlideshowImpl.class).in(Singleton.class);
        bind(TagPresenterFactory.class).to(TagPresenterFactoryImpl.class);
        bind(TreeView.TreePresenter.class).to(TreePresenterImpl.class)
                .in(Singleton.class);
        bind(SelectionChangeEvent.Handler.class).to(TreeSelectionHandler.class)
                .in(Singleton.class);
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

        bind(TreeSelectionHandlerInterface.class).to(TreeSelectionHandler.class);
        bind(LoadNewLocation.class).to(LoadNewLocationImpl.class);
    }
}

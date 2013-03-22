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
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.data.DataManagerImpl;
import com.googlecode.fspotcloud.client.enduseraction.EndUserActionFactory;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.client.main.IClientLoginManager;
import com.googlecode.fspotcloud.client.main.MVPSetup;
import com.googlecode.fspotcloud.client.main.ui.MvpDisplay;
import com.googlecode.fspotcloud.client.main.ui.OpenNewTabImpl;
import com.googlecode.fspotcloud.client.main.ui.ReplaceLocationImpl;
import com.googlecode.fspotcloud.client.main.ui.SchedulerImpl;
import com.googlecode.fspotcloud.client.main.view.MainWindowActivityMapper;
import com.googlecode.fspotcloud.client.main.view.api.IScheduler;
import com.googlecode.fspotcloud.client.main.view.api.OpenNewTab;
import com.googlecode.fspotcloud.client.main.view.api.ReplaceLocation;
import com.googlecode.fspotcloud.client.place.*;
import com.googlecode.fspotcloud.client.place.api.*;
import com.googlecode.fspotcloud.keyboardaction.TimerInterface;
import com.googlecode.fspotcloud.keyboardaction.UIRegistrationBuilder;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.gwt.TimerImpl;

public class AppModule extends AbstractGinModule {

    @Override
    protected void configure() {

        bind(TimerInterface.class).to(TimerImpl.class);
        bind(IScheduler.class).to(SchedulerImpl.class);
        bind(IClientLoginManager.class).to(ClientLoginManager.class).in(Singleton.class);
        bind(DataManager.class).to(DataManagerImpl.class).in(Singleton.class);
        bind(NavigationFlagsHelper.class).in(Singleton.class);
        bind(RasterState.class).in(Singleton.class);
        bind(IRasterer.class).to(Rasterer.class).in(Singleton.class);
        bind(IPlaceController.class).to(PlaceGoToImpl.class).in(Singleton.class);
        bind(Navigator.class).to(NavigatorImpl.class).in(Singleton.class);
        bind(Slideshow.class).to(SlideshowImpl.class).in(Singleton.class);
        bind(OpenNewTab.class).to(OpenNewTabImpl.class);
        bind(ReplaceLocation.class).to(ReplaceLocationImpl.class);

        bind(MVPSetup.class).in(Singleton.class);
        bind(ActionButton.class).annotatedWith(BigToPhotos.class).toProvider(BigToPhotosButtonProvider.class);
        bind(UIRegistrationBuilder.class).to(EndUserActionFactory.class);
        bind(MainWindowActivityMapper.class).in(Singleton.class);
        install(new GinMvpModule(MainWindowActivityMapper.class, HomePlace.class, MvpDisplay.class, MainPlaceHistoryMapper.class));

        install(new ImageViewingMVPModule());
        install(new DashboardMVPModule());
        install(new UserMVPModule());
    }

    @Provides
    @RasterWidth
    public int getRasterWidth() {
        return 5;
    }

    @Provides
    @RasterHeight
    public int getRasterHeight() {
        return 4;
    }

}

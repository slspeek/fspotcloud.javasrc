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

package com.googlecode.fspotcloud.client.useraction;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationBinder;
import com.googlecode.fspotcloud.client.useraction.application.handler.*;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationActionHandler;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.client.useraction.raster.RasterBinder;
import com.googlecode.fspotcloud.client.useraction.raster.handler.SetRasterHandlerFactory;
import com.googlecode.fspotcloud.client.useraction.slideshow.SlideshowBinder;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionModule;
import com.googlecode.fspotcloud.keyboardaction.ModesProvider;


public class UserActionModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new KeyboardActionModule());
        bind(CategoryDef.class).in(Singleton.class);
        bind(ModesProvider.class).to(Modes.class);

        bind(DashboardHandler.class);
        bind(HideControlsHandler.class);
        bind(LoginHandler.class);
        bind(LogoutHandler.class);
        bind(TreeFocusHandler.class);
        bind(ZoomInHandler.class);
        bind(ZoomOutHandler.class);
        bind(ApplicationBinder.class).in(Singleton.class);

        bind(NavigationActionHandler.class);
        bind(NavigationActions.class).in(Singleton.class);

        bind(RasterBinder.class).in(Singleton.class);

        bind(SlideshowBinder.class).in(Singleton.class);
        bind(com.googlecode.fspotcloud.client.useraction.UserActionFactory.class).in(Singleton.class);

        bind(ActionToolbar.class).annotatedWith(MainToolbar.class).toProvider(MainToolbarProvider.class);
        bind(ActionToolbar.class).annotatedWith(SlideshowToolbar.class).toProvider(SlideshowToolbarProvider.class);
        bind(ActionToolbar.class).annotatedWith(EmailConfirmationToolbar.class).toProvider(EmailConfirmationToolbarProvider.class);
        install(new GinFactoryModuleBuilder().build(SetRasterHandlerFactory.class));
    }
}

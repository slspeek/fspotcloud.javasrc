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
import com.google.inject.name.Names;
import com.googlecode.fspotcloud.client.main.event.AbstractActionMap;
import com.googlecode.fspotcloud.client.main.event.ActionFamily;
import com.googlecode.fspotcloud.client.main.event.DefaultActionFamily;
import com.googlecode.fspotcloud.client.main.event.about.AboutMapBuilder;
import com.googlecode.fspotcloud.client.main.event.application.ApplicationMapBuilder;
import com.googlecode.fspotcloud.client.main.event.navigation.NavigationMapBuilder;
import com.googlecode.fspotcloud.client.main.event.raster.RasterMapBuilder;
import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowMapBuilder;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationBinder;
import com.googlecode.fspotcloud.client.useraction.application.handler.*;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationActionHandler;
import com.googlecode.fspotcloud.client.useraction.navigation.NavigationActions;
import com.googlecode.fspotcloud.client.useraction.raster.RasterBinder;
import com.googlecode.fspotcloud.client.useraction.slideshow.SlideshowBinder;
import com.googlecode.fspotcloud.client.view.action.UserActionImpl;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;
import com.googlecode.fspotcloud.client.view.action.api.UserActionFactory;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionModule;
import com.googlecode.fspotcloud.keyboardaction.ModesProvider;


public class UserActionModule extends AbstractGinModule {
    @Override
    protected void configure() {
        install(new KeyboardActionModule());
        bind(CategoryDef.class).in(Singleton.class);
        bind(ModesProvider.class).to(Modes.class);

        bind(AboutAction.class);
        bind(DashboardAction.class);
        bind(HideControlsAction.class);
        bind(LoginHandler.class);
        bind(LogoutHandler.class);
        bind(TreeFocusAction.class);
        bind(ZoomInAction.class);
        bind(ZoomOutAction.class);
        bind(ApplicationBinder.class).in(Singleton.class);

        bind(NavigationActionHandler.class);
        bind(NavigationActions.class).in(Singleton.class);

        bind(RasterBinder.class).in(Singleton.class);

        bind(SlideshowBinder.class).in(Singleton.class);
        bind(com.googlecode.fspotcloud.client.useraction.UserActionFactory.class).in(Singleton.class);

        bind(ActionToolbar.class).toProvider(ToolbarProvider.class);
    }
}

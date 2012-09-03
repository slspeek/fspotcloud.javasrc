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

package com.googlecode.fspotcloud.client.main.event;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.name.Names;
import com.googlecode.fspotcloud.client.main.event.about.AboutMapBuilder;
import com.googlecode.fspotcloud.client.main.event.application.ApplicationMapBuilder;
import com.googlecode.fspotcloud.client.main.event.navigation.NavigationMapBuilder;
import com.googlecode.fspotcloud.client.main.event.raster.RasterMapBuilder;
import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowMapBuilder;
import com.googlecode.fspotcloud.client.view.action.UserActionImpl;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;
import com.googlecode.fspotcloud.client.view.action.api.UserActionFactory;


public class EventModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(AbstractActionMap.class).annotatedWith(Names.named("application"))
                .to(ApplicationMapBuilder.class);
        bind(AbstractActionMap.class).annotatedWith(Names.named("about"))
                .to(AboutMapBuilder.class);
        bind(AbstractActionMap.class).annotatedWith(Names.named("raster"))
                .to(RasterMapBuilder.class);
        bind(AbstractActionMap.class).annotatedWith(Names.named("navigation"))
                .to(NavigationMapBuilder.class);
        bind(AbstractActionMap.class).annotatedWith(Names.named("slideshow"))
                .to(SlideshowMapBuilder.class);
        bind(ActionFamily.class).to(DefaultActionFamily.class);

        install(new GinFactoryModuleBuilder().implement(UserAction.class,
                UserActionImpl.class).build(UserActionFactory.class));
    }
}

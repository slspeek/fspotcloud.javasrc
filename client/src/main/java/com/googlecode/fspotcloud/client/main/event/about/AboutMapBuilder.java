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

package com.googlecode.fspotcloud.client.main.event.about;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.event.AbstractActionMap;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;
import com.googlecode.fspotcloud.client.view.action.api.UserActionFactory;


public class AboutMapBuilder extends AbstractActionMap {
    private Resources resources;

    @Inject
    public AboutMapBuilder(UserActionFactory userActionFactory,
                           Resources resources) {
        super(userActionFactory, "About");
        this.resources = resources;
    }

    public void buildMap() {
        put(AboutType.PROJECT_HOSTING, resources.projectSiteIcon());
        put(AboutType.F_SPOT, resources.fspotIcon());
        put(AboutType.MAVEN, resources.mavenIcon());
        put(AboutType.LICENSE, resources.licenceIcon());
        put(AboutType.PROTON, resources.protonIcon());
        put(AboutType.STEVEN, resources.authorIcon());
    }

    private void put(ActionDef actionDef, ImageResource icon) {
        put(actionDef, icon, new AboutEventProvider(actionDef));
    }
}

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

package com.googlecode.fspotcloud.client.main.view.factory;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.ui.UserButtonViewImpl;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonView;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonViewFactory;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;


public class UserButtonViewFactoryImpl implements UserButtonViewFactory {
    private final Resources resources;

    @Inject
    public UserButtonViewFactoryImpl(Resources resources) {
        super();
        this.resources = resources;
    }

    @Override
    public UserButtonView get(UserAction action) {
        ImageResource img = action.getIcon();
        UserButtonView result;

        if (img != null) {
            result = new UserButtonViewImpl(resources, img);
        } else {
            result = new UserButtonViewImpl(resources);
        }

        return result;
    }
}

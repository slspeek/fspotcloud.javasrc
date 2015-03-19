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

package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;


public interface AdminResources extends ClientBundle {
    @Source("admin.css")
    Style style();

    public interface Style extends CssResource {
        String copyleft();

        String editUsergroup();

        String editUsergroupHeader();

        String manageUsers();

        String usergroupName();

        String myUsergroups();

        String myUsergroupsTable();

        String publicCheckbox();

        String tree();

        String main();

        String footer();

        String titleLabel();

        String importedTag();

        String tag();

        String headerLabel();

        String propertyLabel();

        String propertyInput();

        String propertyValue();

        String propertyNumberValue();

        String labelDetails();

        String globalActions();

        String tableLayout();

        String tableLayoutLayer();

        String tableHeader();
    }
}

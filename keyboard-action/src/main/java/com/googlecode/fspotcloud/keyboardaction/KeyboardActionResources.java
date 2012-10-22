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

package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;


public interface KeyboardActionResources extends ClientBundle {

    @Source("actions.css")
    Style style();

    @Source("images/Help.png")
    ImageResource helpIcon();

    public interface Style extends CssResource {

        String button();

        String demo();

        String demoPopup();

        String helpTitleRow();

        String helpKey();

        String helpBody();

        String helpLeftBody();

        String helpRightBody();

        String helpBigBody();

        String helpClose();

        String helpPopup();

        String helpSeparator();

        String helpTitle();

        String buttonPanelBlock();

        String helpRow();

        String helpDescription();

        String helpCategory();

        String helpCategoryTitle();

        String helpName();

        String helpActionIcon();

        String helpKeys();

        String popUpMenu();

        String menuItem();

        String menuButton();

        String menuItemText();

        String menuItemShortcut();
    }
}

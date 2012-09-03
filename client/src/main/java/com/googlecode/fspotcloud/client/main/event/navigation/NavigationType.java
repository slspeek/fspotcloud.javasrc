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

package com.googlecode.fspotcloud.client.main.event.navigation;

import com.google.gwt.event.dom.client.KeyCodes;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;

public enum NavigationType implements ActionDef {
    HOME("home", "Home", "Go to the first image of the category",
            new KeyStroke(KeyCodes.KEY_HOME), null),
    PAGE_UP("page-up", "Page up", "Go one page back",
            new KeyStroke(KeyCodes.KEY_PAGEUP), null),
    ROW_UP("row-up", "Row up", "Go one row back",
            new KeyStroke(KeyCodes.KEY_UP), null),
    BACK("back", "Back", "Previous image in this category",
            new KeyStroke(KeyCodes.KEY_LEFT), null),
    NEXT("next", "Next", "Next image in this category",
            new KeyStroke(KeyCodes.KEY_RIGHT), null),
    ROW_DOWN("row-down", "Row down", "Go one row down",
            new KeyStroke(KeyCodes.KEY_DOWN), null),
    PAGE_DOWN("page-down", "Page down", "Go one page forward",
            new KeyStroke(KeyCodes.KEY_PAGEDOWN), null),
    END("end", "End", "Go to the last image of the category",
            new KeyStroke(KeyCodes.KEY_END), null);
    private final KeyStroke key;
    private final KeyStroke alternateKey;
    private final String caption;
    private final String id;
    private final String description;

    private NavigationType(String id, String caption, String description,
                           KeyStroke key, KeyStroke alternateKey) {
        this.key = key;
        this.alternateKey = alternateKey;
        this.caption = caption;
        this.id = id;
        this.description = description;
    }

    @Override
    public KeyStroke getAlternateKey() {
        return alternateKey;
    }

    @Override
    public KeyStroke getKey() {
        return key;
    }

    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getId() {
        return id;
    }
}

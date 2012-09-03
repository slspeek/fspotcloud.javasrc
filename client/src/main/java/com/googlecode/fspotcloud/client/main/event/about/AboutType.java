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

import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.api.ActionDef;

public enum AboutType implements ActionDef {
    LICENSE("license", "License", "View the license", new KeyStroke('L'), null),
    PROJECT_HOSTING("project-hosting", "Project site",
            "Go to the site on Google Project Hosting", new KeyStroke('J'), null),
    F_SPOT("f-spot", "F-Spot", "Go to the F-Spot site", new KeyStroke('N'), null),
    MAVEN("maven", "Maven site", "Go to the Maven generated site",
            new KeyStroke('M'), null),
    PROTON("proton", "Proton radio", "Go to the Proton site",
            new KeyStroke('P'), null),
    STEVEN("steven", "Authors website", "Go to the authors website",
            new KeyStroke('Z'), null);
    private final KeyStroke key;
    private final KeyStroke alternateKey;
    private final String caption;
    private final String id;
    private final String description;

    private AboutType(String id, String caption, String description,
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

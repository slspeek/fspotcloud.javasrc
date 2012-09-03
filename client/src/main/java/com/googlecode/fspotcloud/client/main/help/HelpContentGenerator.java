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

package com.googlecode.fspotcloud.client.main.help;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;


public class HelpContentGenerator {
    private final Resources.Style style;

    @Inject
    public HelpContentGenerator(Resources res) {
        super();
        this.style = res.style();
    }

    private String getHelpRow(String key, String altKey1, String altKey2,
                              String description, ImageResource icon) {
        String row = "";
        row += ("<span class='" + style.helpKey() + "'>" + key + "</span>");

        if (altKey1 != null) {
            row += (" or <span class='" + style.helpKey() + "'>" + altKey1 +
                    "</span>");

            if (altKey2 != null) {
                row += (" or <span class='" + style.helpKey() + "'>" + altKey2 +
                        "</span>");
            }
        }

        row += "</td>";

        row += ("<td><span class='" + style.helpSeparator() +
                "'>:</span></td>");
        row += "<td>";

        if (icon != null) {
            row += ("<img src='" + icon.getURL() + "' />");
        }

        row += "</td>";
        row += ("<td><span class='" + style.helpDescription() + "'>" +
                description + "</span>");

        return row;
    }

    public String getHelpText(UserAction shortcut) {
        String key;
        String altKey;
        KeyStroke stroke;
        KeyStroke altStroke;
        stroke = shortcut.getKey();
        altStroke = shortcut.getAlternateKey();
        key = stroke.getKeyString();

        if (altStroke != null) {
            altKey = altStroke.getKeyString();

            return getHelpRow(key, altKey, null, shortcut.getDescription(),
                    shortcut.getIcon());
        } else {
            return getHelpRow(key, null, null, shortcut.getDescription(),
                    shortcut.getIcon());
        }
    }
}

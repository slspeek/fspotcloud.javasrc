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

package com.googlecode.fspotcloud.client.main.view;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.event.ActionFamily;
import com.googlecode.fspotcloud.client.main.event.ActionMap;
import com.googlecode.fspotcloud.client.main.help.HelpContentGenerator;
import com.googlecode.fspotcloud.client.main.view.api.PopupView;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;


public class HelpPresenter implements PopupView.PopupPresenter {
    private final PopupView popupView;
    private final HelpContentGenerator generator;
    private String helptext;
    private final ActionFamily actions;

    @Inject
    public HelpPresenter(ActionFamily actions, HelpContentGenerator generator,
                         PopupView popupView) {
        this.actions = actions;
        this.generator = generator;
        this.popupView = popupView;
        popupView.setText(initHelpText());
        popupView.setTitle("Keyboard Shortcuts");
        popupView.setPresenter(this);
    }

    private String initHelpText() {
        helptext = "<table>";
        helptext += "<tr><td>";
        helptext += getHelpGroup(actions.get("Navigation"));
        helptext += "</td><td>";
        helptext += getHelpGroup(actions.get("Raster"));
        helptext += "</td></tr><tr><td>";
        helptext += getHelpGroup(actions.get("Application"));
        helptext += "</td><td>";
        helptext += getHelpGroup(actions.get("Slideshow"));
        helptext += "</td></tr>";
        helptext += "</table>";

        return helptext;
    }

    private String getHelpGroup(ActionMap group) {
        String title = group.getDescription();
        String helptext = "<table style='text-align: left'>";
        helptext += "<tr><th colspan=4 style='text-align: center'>";
        helptext += title;
        helptext += "</th></tr></th>";

        for (UserAction shortcut : group.allActions()) {
            helptext += ("<tr><td>" + generator.getHelpText(shortcut) +
                    "</td></tr>");
        }

        helptext += "</table>";

        return helptext;
    }

    public void show() {
        popupView.setGlassEnabled(true);
        popupView.center();
        popupView.show();
        popupView.focus();
    }

    public void hide() {
        popupView.hide();
    }

    @Override
    public boolean isShowing() {
        return popupView.isShowing();
    }
}

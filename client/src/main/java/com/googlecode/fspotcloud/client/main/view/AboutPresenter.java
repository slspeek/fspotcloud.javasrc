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
import com.googlecode.fspotcloud.client.view.action.api.IGlobalShortcutController;
import com.googlecode.fspotcloud.client.view.action.api.IGlobalShortcutController.Mode;
import com.googlecode.fspotcloud.client.main.event.AbstractActionMap;
import com.googlecode.fspotcloud.client.main.event.ActionMap;
import com.googlecode.fspotcloud.client.main.help.HelpContentGenerator;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.view.api.PopupView;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import javax.inject.Named;


public class AboutPresenter implements PopupView.PopupPresenter {
    private final PopupView popupView;
    private final HelpContentGenerator generator;
    private String helptext;
    private final ActionMap actions;
    private Resources resources;
    private final IGlobalShortcutController globalShortcutController;

    @Inject
    public AboutPresenter(IGlobalShortcutController globalShortcutController,
                          @Named("about")
                          AbstractActionMap actions, HelpContentGenerator generator,
                          PopupView popupView, Resources resources) {
        this.actions = actions;
        actions.buildMap();
        this.globalShortcutController = globalShortcutController;
        this.resources = resources;
        this.generator = generator;
        this.popupView = popupView;
        popupView.setText(initHelpText());
        popupView.setTitle("About");
    }

    private String initHelpText() {
        helptext = "<table style='text-align: left'>";
        helptext += getAboutGroup(actions);

        helptext += "<tr><td colspan=4 style='text-align: center'>";

        String version = resources.getVersion().getText();
        helptext += version.replaceAll("\n", "<br/>");
        helptext += "</td></tr>";

        helptext += "</table>";

        return helptext;
    }

    private String getAboutGroup(ActionMap group) {
        for (UserAction shortcut : group.allActions()) {
            helptext += ("<tr><td>" + generator.getHelpText(shortcut) +
                    "</td></tr>");
        }

        return helptext;
    }

    public void show() {
        popupView.setGlassEnabled(true);
        popupView.center();
        popupView.show();
        popupView.focus();
        globalShortcutController.setMode(Mode.ABOUT);
    }

    public void hide() {
        popupView.hide();
        globalShortcutController.setMode(Mode.TAG_VIEW);
    }

    @Override
    public boolean isShowing() {
        return popupView.isShowing();
    }
}

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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.googlecode.fspotcloud.client.main.event.ActionFamily;
import com.googlecode.fspotcloud.client.main.event.ActionMap;
import com.googlecode.fspotcloud.client.main.event.application.ApplicationType;
import com.googlecode.fspotcloud.client.main.event.raster.RasterType;
import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowType;
import com.googlecode.fspotcloud.client.main.view.ButtonPanelPresenterImpl;
import com.googlecode.fspotcloud.client.main.view.api.ButtonPanelView;
import com.googlecode.fspotcloud.client.main.view.api.ButtonPanelView.ButtonPanelPresenter;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonPresenterFactory;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonView;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import java.util.logging.Logger;


public class ButtonPanelPresenterProvider implements Provider<ButtonPanelView.ButtonPanelPresenter> {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(ButtonPanelPresenterProvider.class.getName());
    private final ButtonPanelView buttonPanelView;
    private final ActionFamily allActions;
    private final UserButtonPresenterFactory buttonPresenterFactory;

    @Inject
    public ButtonPanelPresenterProvider(
            @Named("Main")
            ButtonPanelView buttonPanelView,
            ActionFamily allActions,
            UserButtonPresenterFactory buttonPresenterFactory) {
        super();
        this.buttonPanelView = buttonPanelView;
        this.allActions = allActions;
        this.buttonPresenterFactory = buttonPresenterFactory;
        init();
    }

    public void init() {
        int total = 0;
        ActionMap nav = allActions.get("Navigation");
        total += nav.allActions().size();
        total++; //Slideshow
        total++; //Email

        ActionMap app = allActions.get("Application");
        total += 5;
        buttonPanelView.setWidgetCount(total);

        addActionGroup(nav);

        ActionMap actions = allActions.get("Slideshow");
        addAction(actions.get(SlideshowType.SLIDESHOW_START));

        ActionMap rasterActions = allActions.get("Raster");
        addAction(rasterActions.get(RasterType.MAIL_FULLSIZE));

        addAction(app.get(ApplicationType.TOGGLE_HELP));
        addAction(app.get(ApplicationType.ABOUT));
        addAction(app.get(ApplicationType.DASHBOARD));
        addAction(app.get(ApplicationType.LOGIN));
        addAction(app.get(ApplicationType.LOGOUT));
    }

    private void addActionGroup(ActionMap group) {
        for (UserAction action : group.allActions()) {
            addAction(action);
        }
    }

    private void addAction(UserAction action) {
        UserButtonView.UserButtonPresenter buttonPresenter = buttonPresenterFactory.get(action);
        buttonPresenter.init();
        buttonPanelView.add(buttonPresenter.getView());
    }

    @Override
    public ButtonPanelPresenter get() {
        return new ButtonPanelPresenterImpl(buttonPanelView);
    }
}

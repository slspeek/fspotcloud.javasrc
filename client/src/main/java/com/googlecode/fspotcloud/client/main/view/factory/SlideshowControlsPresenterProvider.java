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

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.googlecode.fspotcloud.client.main.event.AbstractActionMap;
import com.googlecode.fspotcloud.client.main.event.slideshow.SlideshowType;
import com.googlecode.fspotcloud.client.main.view.SlideshowControlsPresenter;
import com.googlecode.fspotcloud.client.main.view.api.ButtonPanelView;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowView;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonPresenterFactory;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonView;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import java.util.logging.Logger;


public class SlideshowControlsPresenterProvider implements Provider<SlideshowControlsPresenter> {
    private final Logger log = Logger.getLogger(SlideshowControlsPresenterProvider.class.getName());
    private final AbstractActionMap actions;
    private final UserButtonPresenterFactory buttonPresenterFactory;
    private final SlideshowView slideshowView;
    private final SlideshowView.SlideshowPresenter slideshowPresenter;
    private final ButtonPanelView buttonPanelView;

    @Inject
    public SlideshowControlsPresenterProvider(
            @Named("Slideshow")
            ButtonPanelView buttonPanelView,
            @Named("slideshow")
            AbstractActionMap actions,
            UserButtonPresenterFactory buttonPresenterFactory,
            SlideshowView slideshowView,
            SlideshowView.SlideshowPresenter slideshowPresenter) {
        super();
        this.buttonPanelView = buttonPanelView;
        this.slideshowPresenter = slideshowPresenter;
        this.actions = actions;
        actions.buildMap();
        this.buttonPresenterFactory = buttonPresenterFactory;
        this.slideshowView = slideshowView;
        log.info("created");
        init();
    }

    public void init() {
        buttonPanelView.setWidgetCount(actions.allActions().size() + 1);
        addAction(actions.get(SlideshowType.SLIDESHOW_SLOWER));
        addAction(actions.get(SlideshowType.SLIDESHOW_START));
        addAction(actions.get(SlideshowType.SLIDESHOW_PAUSE));

        Widget w = slideshowView.asWidget();
        buttonPanelView.add(w);

        addAction(actions.get(SlideshowType.SLIDESHOW__END));
        addAction(actions.get(SlideshowType.SLIDESHOW_FASTER));
    }

    private void addAction(UserAction action) {
        UserButtonView.UserButtonPresenter buttonPresenter = buttonPresenterFactory.get(action);
        buttonPresenter.init();
        buttonPanelView.add(buttonPresenter.getView());
    }

    @Override
    public SlideshowControlsPresenter get() {
        return new SlideshowControlsPresenter(slideshowPresenter,
                buttonPanelView);
    }
}

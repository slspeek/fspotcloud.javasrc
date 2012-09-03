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

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonView;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonViewFactory;
import com.googlecode.fspotcloud.client.view.action.KeyStroke;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;

import java.util.logging.Logger;


public class UserButtonPresenterImpl implements UserButtonView.UserButtonPresenter {
    private final Logger log = Logger.getLogger(UserButtonPresenterImpl.class.getName());
    private final UserAction action;
    private final UserButtonViewFactory viewFactory;
    private UserButtonView view;

    @Inject
    public UserButtonPresenterImpl(@Assisted
                                   UserAction action, UserButtonViewFactory viewFactory) {
        super();
        this.action = action;
        this.viewFactory = viewFactory;
    }

    @Override
    public void init() {
        view = viewFactory.get(action);
        view.setPresenter(this);
        initButton();
    }

    private void initButton() {
        String caption = action.getCaption();

        if (action.getIcon() == null) {
            view.setCaption(caption);
        }

        String tooltip = getTooltip();
        view.setTooltip(tooltip);
        view.setDebugId(action.getId());
    }

    private String getTooltip() {
        KeyStroke key = action.getKey();
        KeyStroke altKey = action.getAlternateKey();
        String caption = action.getCaption();
        String tip = caption + " ( " + key.getKeyString() + " )";

        if (altKey != null) {
            tip = caption + "( " + key.getKeyString() + " or " +
                    altKey.getKeyString() + " )";
        }

        return tip;
    }

    @Override
    public void buttonClicked() {
        action.run();
    }

    @Override
    public Widget getView() {
        return view.asWidget();
    }
}

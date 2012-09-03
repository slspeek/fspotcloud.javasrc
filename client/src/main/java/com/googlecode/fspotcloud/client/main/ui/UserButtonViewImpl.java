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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.UserButtonView;


public class UserButtonViewImpl extends PushButton implements UserButtonView {
    private UserButtonPresenter presenter;
    private final Resources resources;

    @Inject
    public UserButtonViewImpl(Resources resources, ImageResource icon) {
        super(new Image(icon));
        this.resources = resources;
        setStyleName(resources.style().button());
    }

    @Inject
    public UserButtonViewImpl(Resources resources) {
        this.resources = resources;
        setStyleName(resources.style().button());
    }

    @Override
    public void setPresenter(UserButtonPresenter presenter) {
        this.presenter = presenter;
        registerClickEvents();
    }

    private void registerClickEvents() {
        // Maybe unregister old one first?
        // For now setPresenter must be called only once.
        addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (presenter != null) {
                    presenter.buttonClicked();
                }
            }
        });
    }

    @Override
    public void setCaption(String caption) {
        setText(caption);
    }

    @Override
    public void setTooltip(String tooltip) {
        asWidget().setTitle(tooltip);
    }

    @Override
    public void setDebugId(String id) {
        ensureDebugId(id);
    }
}

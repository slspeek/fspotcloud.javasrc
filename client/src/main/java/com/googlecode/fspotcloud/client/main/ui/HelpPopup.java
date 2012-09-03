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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.PopupView;

import java.util.logging.Logger;


public class HelpPopup extends PopupPanel implements PopupView {
    private final Logger log = Logger.getLogger(HelpPopup.class.getName());
    private static HelpPopupUiBinder uiBinder = GWT.create(HelpPopupUiBinder.class);
    @UiField
    FocusPanel focusPanel;
    @UiField
    DivElement helpBodyDiv;
    @UiField
    DivElement titleSpan;
    private PopupView.PopupPresenter presenter;

    @Inject
    public HelpPopup(Resources resources) {
        super(true);
        setWidget(uiBinder.createAndBindUi(this));
        setStyleName(resources.style().helpPopup());
        focusPanel.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                log.info("Keydown in help-popup " + event);
                presenter.hide();
            }
        });
    }

    public void setText(String text) {
        helpBodyDiv.setInnerHTML(text);
    }

    public void setTitle(String text) {
        titleSpan.setInnerHTML(text);
    }

    @Override
    public void focus() {
        focusPanel.setFocus(true);
    }

    public void setPresenter(PopupView.PopupPresenter presenter) {
        this.presenter = presenter;
    }

    interface HelpPopupUiBinder extends UiBinder<FocusPanel, HelpPopup> {
    }
}

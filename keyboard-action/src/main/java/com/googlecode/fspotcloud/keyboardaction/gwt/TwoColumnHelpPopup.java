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

package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.HelpConfig;
import com.googlecode.fspotcloud.keyboardaction.HelpResources;

import java.util.logging.Logger;


public class TwoColumnHelpPopup extends PopupPanel {
    private final Logger log = Logger.getLogger(TwoColumnHelpPopup.class.getName());
    private static final HelpPopupUiBinder uiBinder = GWT.create(HelpPopupUiBinder.class);
    @UiField
    Anchor closeAnchor;
    @UiField
    SpanElement helpBodyLeft;
    @UiField
    SpanElement helpBodyRight;
    @UiField
    Label titleLabel;
    private final HelpResources helpResources;
    private HelpConfig helpConfig;

    @Inject
    private TwoColumnHelpPopup(HelpResources helpResources) {
        super(true);
        this.helpResources = helpResources;
        setWidget(uiBinder.createAndBindUi(this));
        addStyleName(helpResources.style().helpPopup());
    }

    public void setHelpConfig(HelpConfig helpConfig) {
        this.helpConfig = helpConfig;
        setTitle(helpConfig.getTitle());
    }

    public void setLeft(SafeHtml text) {
        helpBodyLeft.setInnerSafeHtml(text);
    }

    public void setRight(SafeHtml text) {
        helpBodyRight.setInnerSafeHtml(text);
    }

    public void setTitle(String text) {
        titleLabel.setText(text);
    }

    interface HelpPopupUiBinder extends UiBinder<HTMLPanel, TwoColumnHelpPopup> {
    }

    @UiHandler("closeAnchor")
    public void handleClose(ClickEvent clickEvent) {
        hide();
    }

}
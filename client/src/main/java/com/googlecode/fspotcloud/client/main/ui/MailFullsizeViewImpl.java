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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.client.enduseraction.user.UserActions;
import com.googlecode.fspotcloud.client.main.gin.BigButtonFactory;
import com.googlecode.fspotcloud.client.main.view.api.MailFullsizeView;
import com.googlecode.fspotcloud.client.main.view.factory.ImageViewFactoryImpl;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButton;

import java.util.logging.Logger;


public class MailFullsizeViewImpl extends Composite implements MailFullsizeView {
    private final Logger log = Logger.getLogger(MailFullsizeViewImpl.class.getName());
    private static final MailFullsizeImplUiBinder uiBinder = GWT.create(MailFullsizeImplUiBinder.class);
    @UiField
    Label statusLabel;
    @UiField(provided = true)
    ActionButton mail;
    @UiField(provided = true)
    ActionButton cancel;
    @UiField(provided = true)
    ImageViewImpl imageView;

    @Inject
    public MailFullsizeViewImpl(UserActions userActions,
                                DashboardActions dashboardActions,
                                BigButtonFactory factory,
                                ImageViewFactoryImpl imageViewFactory) {
        mail = factory.getButton(userActions.doMailFullsize);
        cancel = factory.getButton(dashboardActions.toPhotos);
        this.imageView = (ImageViewImpl) imageViewFactory.get("0x0");
        initWidget(uiBinder.createAndBindUi(this));
        statusLabel.ensureDebugId("status");
    }

    public ImageViewImpl getImageView() {
        return imageView;
    }

    @Override
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }


    interface MailFullsizeImplUiBinder extends UiBinder<Widget, MailFullsizeViewImpl> {
    }
}

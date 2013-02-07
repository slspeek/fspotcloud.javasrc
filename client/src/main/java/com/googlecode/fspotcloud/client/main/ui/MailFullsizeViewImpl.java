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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.client.main.view.api.MailFullsizeView;
import com.googlecode.fspotcloud.client.main.view.factory.ImageViewFactoryImpl;

import java.util.logging.Logger;


public class MailFullsizeViewImpl extends Composite implements MailFullsizeView {
    private final Logger log = Logger.getLogger(MailFullsizeViewImpl.class.getName());
    private static final MailFullsizeImplUiBinder uiBinder = GWT.create(MailFullsizeImplUiBinder.class);
    private final ImageView imageView;
    private MailFullsizePresenter presenter;
    @UiField
    Label statusLabel;
    @UiField
    PushButton mail;
    @UiField
    PushButton cancel;

    @Inject
    public MailFullsizeViewImpl(ImageViewFactoryImpl imageViewFactory) {
        this.imageView = imageViewFactory.get("0x0");
        initWidget(uiBinder.createAndBindUi(this));
        statusLabel.ensureDebugId("status");
    }

    @Override
    @UiFactory
    public ImageViewImpl getImageView() {
        return (ImageViewImpl) imageView;
    }

    @Override
    public void setPresenter(MailFullsizePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    @Override
    public void setImageUrl(String url) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @UiHandler("mail")
    public void onPush(ClickEvent e) {
        presenter.mailImage();
    }

    @UiHandler("cancel")
    public void onCancel(ClickEvent e) {
        presenter.cancel();
    }

    interface MailFullsizeImplUiBinder extends UiBinder<Widget, MailFullsizeViewImpl> {
    }
}

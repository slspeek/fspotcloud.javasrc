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
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;
import com.reveregroup.gwt.imagepreloader.FitImage;

import java.util.logging.Logger;


public class ImageViewImpl extends ResizeComposite implements ImageView {
    private final Logger log = Logger.getLogger(ImageViewImpl.class.getName());
    private static ImageViewImplUiBinder uiBinder = GWT.create(ImageViewImplUiBinder.class);
    private final TimerInterface timer;
    @UiField
    Label info;
    @UiField
    FitImage image;
    @UiField
    LayoutPanel layout;
    private ImageView.ImagePresenter presenter;
    private final Resources resources;

    @Inject
    public ImageViewImpl(@Assisted
                         String location, TimerInterface timer, Resources resources) {
        this.timer = timer;
        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
        init(location);
    }

    private void init(String location) {
        image.ensureDebugId("image-view-" + location);
    }

    @Override
    public void setImageUrl(final String url) {
        image.setUrl(url);
    }

    @UiHandler("image")
    public void imageClicked(ClickEvent event) {
        log.info("image clicked");
        this.presenter.imageClicked();
    }

    @UiHandler("image")
    public void infoHover(MouseMoveEvent event) {
        layout.setWidgetBottomHeight(info, 0, Unit.CM, 16, Unit.PX);
        layout.animate(500);
        hideLabelLater(3000);
    }

    public void hideLabelLater(final int duration) {
        timer.setRunnable(new Runnable() {
            @Override
            public void run() {
                layout.setWidgetBottomHeight(info, 0, Unit.CM, 0, Unit.PX);
                layout.animate(500);
            }
        });
        timer.schedule(duration);
    }

    @Override
    public void setPresenter(ImagePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setDescription(String text) {
        info.setText(text);
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected) {
            setStyleName(resources.style().selectedImage());
        } else {
            setStyleName(resources.style().image());
        }
    }

    @Override
    public void onResize() {
        image.setMaxSize(getOffsetWidth(), getOffsetHeight());
        super.onResize();
    }

    @Override
    public void adjustSize() {
        log.info("Called adjust size");
        onResize();
    }

    interface ImageViewImplUiBinder extends UiBinder<LayoutPanel, ImageViewImpl> {
    }
}

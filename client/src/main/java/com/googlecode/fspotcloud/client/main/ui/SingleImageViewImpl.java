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
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.googlecode.fspotcloud.client.main.view.api.ButtonPanelView;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterView;
import com.googlecode.fspotcloud.client.main.view.api.SingleImageView;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;

import java.util.logging.Logger;


public class SingleImageViewImpl extends Composite implements SingleImageView,
        MouseMoveHandler {
    private final Logger log = Logger.getLogger(SingleImageViewImpl.class.getName());
    private static SingleImageViewImplUiBinder uiBinder = GWT.create(SingleImageViewImplUiBinder.class);
    private final ButtonPanelView buttonPanelView;
    private final ImageRasterView imageRasterView;
    private final TimerInterface timer;
    @UiField
    LayoutPanel layout;
    private SingleImageView.SingleImagePresenter presenter;

    @Inject
    public SingleImageViewImpl(ImageRasterView imageRasterView,
                               @Named("Slideshow")
                               ButtonPanelView buttonPanelView, TimerInterface timer) {
        this.timer = timer;
        this.buttonPanelView = buttonPanelView;
        this.imageRasterView = imageRasterView;
        initWidget(uiBinder.createAndBindUi(this));
        layout.addDomHandler(this, MouseMoveEvent.getType());
        log.info("created");
    }

    @UiFactory
    public ButtonPanelViewImpl getButtonPanelView() {
        return (ButtonPanelViewImpl) buttonPanelView;
    }

    @UiFactory
    public ImageRasterViewImpl getImageRasterView() {
        return (ImageRasterViewImpl) imageRasterView;
    }

    public void showControls(int duration) {
        layout.setWidgetBottomHeight(buttonPanelView, 0, Unit.CM, 50, Unit.PX);
        layout.animate(duration);
    }

    public void hideControls(int duration) {
        layout.setWidgetBottomHeight(buttonPanelView, 0, Unit.CM, 0, Unit.PX);
        layout.animate(duration);
    }

    @Override
    public void hideControlsLater(int visibleDuration) {
        timer.setRunnable(new Runnable() {
            @Override
            public void run() {
                hideControls(1000);
            }
        });
        timer.schedule(visibleDuration);
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        showControls(600);
        hideControlsLater(6000);
    }

    @Override
    public void setPresenter(SingleImagePresenter singleImageActivity) {
        this.presenter = singleImageActivity;
    }

    interface SingleImageViewImplUiBinder extends UiBinder<LayoutPanel, SingleImageViewImpl> {
    }
}

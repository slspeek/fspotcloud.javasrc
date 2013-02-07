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
import com.googlecode.fspotcloud.client.main.view.api.DoubleImageView;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowView;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;
import com.googlecode.fspotcloud.client.useraction.SlideshowToolbar;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;

import java.util.logging.Logger;


public class SlideshowViewImpl extends Composite implements SlideshowView,
        MouseMoveHandler {
    private final Logger log = Logger.getLogger(SlideshowViewImpl.class.getName());
    private static final SlideshowViewImplUiBinder uiBinder = GWT.create(SlideshowViewImplUiBinder.class);
    private final ActionToolbar actionToolbar;
    private final DoubleImageView doubleImageView;
    private final TimerInterface timer;
    @UiField
    LayoutPanel layout;
    private SlideshowPresenter presenter;

    @Inject
    public SlideshowViewImpl(DoubleImageView imageView,
                             @SlideshowToolbar ActionToolbar actionToolbar,
                             TimerInterface timer) {
        this.actionToolbar = actionToolbar;
        this.timer = timer;
        this.doubleImageView = imageView;
        initWidget(uiBinder.createAndBindUi(this));
        layout.addDomHandler(this, MouseMoveEvent.getType());
        log.info("created");
    }

    @UiFactory
    public ActionToolbar getButtonPanelView() {
        return actionToolbar;
    }

    @UiFactory
    public DoubleImageViewImpl getDoubleImageView() {
        return (DoubleImageViewImpl) doubleImageView;
    }

    public void showControls(int duration) {
        layout.setWidgetBottomHeight(actionToolbar, 0, Unit.CM, 50, Unit.PX);
        layout.animate(duration);
    }

    public void hideControls(int duration) {
        layout.setWidgetBottomHeight(actionToolbar, 0, Unit.CM, 0, Unit.PX);
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
    public void setPresenter(SlideshowPresenter slideshowActivity) {
        this.presenter = slideshowActivity;
    }

    interface SlideshowViewImplUiBinder extends UiBinder<LayoutPanel, SlideshowViewImpl> {
    }
}

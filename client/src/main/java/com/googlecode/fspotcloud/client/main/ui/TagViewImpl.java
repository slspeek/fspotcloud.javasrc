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
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.BasicTreeView;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterView;
import com.googlecode.fspotcloud.client.main.view.api.TagView;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.enduseraction.MainToolbar;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;

import java.util.logging.Level;
import java.util.logging.Logger;


public class TagViewImpl extends Composite implements TagView,
        MouseOverHandler,
        MouseOutHandler {
    private static final int TREE_VIEW_WIDTH_PCT = 22;
    private static final int IMAGE_PANEL_WIDTH_PCT = 100 - TREE_VIEW_WIDTH_PCT;
    private static final int BUTTON_PANEL_HEIGHT_PCT = 6;
    private static final int IMAGEPANEL_HEIGHT_PCT = 100 -
            BUTTON_PANEL_HEIGHT_PCT;
    private final Logger log = Logger.getLogger(TagViewImpl.class.getName());
    private static final TagViewImplUiBinder uiBinder = GWT.create(TagViewImplUiBinder.class);
    static int ID;
    @UiField
    HTML horizontalFocusPanel;
    @UiField
    HTML verticalFocusPanel;
    @UiField
    LayoutPanel mainPanel;
    final TreeView treeView;
    final ImageRasterView imageRasterView;
    private final TimerInterface timer;
    @SuppressWarnings("unused")
    private TagPresenter presenter;
    int id = ++ID;
    private final ActionToolbar actionToolbar;
    private boolean autoHide;

    @Inject
    public TagViewImpl(@BasicTreeView TreeView treeView,
                       ImageRasterView imageRasterView,
                       TimerInterface timer,
                       @MainToolbar ActionToolbar actionToolbar) {
        this.timer = timer;
        this.treeView = treeView;
        this.actionToolbar = actionToolbar;
        imageRasterView.asWidget().addDomHandler(this, MouseOverEvent.getType());
        imageRasterView.asWidget().addDomHandler(this, MouseOutEvent.getType());
        this.imageRasterView = imageRasterView;
        initWidget(uiBinder.createAndBindUi(this));
        log.info("Created: " + id);
    }

    @UiHandler("horizontalFocusPanel")
    public void infoHover(MouseOverEvent event) {
        log.log(Level.FINER, "horizontal mouse over");
        cancelHiding();
        animateControlsIn(600);
    }

    @UiFactory
    ActionToolbar getActionToolbar() {
        return actionToolbar;
    }

    @UiHandler("verticalFocusPanel")
    public void verticalMousePanel(MouseOverEvent event) {
        cancelHiding();
        animateControlsIn(600);
        log.log(Level.FINER, "vertical mouse over");
    }

    public void animateControlsIn(int duration) {
        if(!autoHide) {
            duration = 0;
        }
        cancelHiding();
        mainPanel.setWidgetBottomHeight(actionToolbar, 0, Unit.CM,
                BUTTON_PANEL_HEIGHT_PCT, Unit.PCT);
        mainPanel.setWidgetTopHeight(imageRasterView, 0, Unit.CM,
                IMAGEPANEL_HEIGHT_PCT, Unit.PCT);
        mainPanel.setWidgetRightWidth(imageRasterView, 0, Unit.CM,
                IMAGE_PANEL_WIDTH_PCT, Unit.PCT);
        mainPanel.setWidgetLeftWidth(treeView, 0, Unit.PCT,
                TREE_VIEW_WIDTH_PCT, Unit.PCT);
        mainPanel.setWidgetTopHeight(treeView, 0, Unit.PCT,
                IMAGEPANEL_HEIGHT_PCT, Unit.PCT);
        mainPanel.setWidgetBottomHeight(horizontalFocusPanel, 0, Unit.PCT, 0,
                Unit.PCT);
        mainPanel.setWidgetLeftWidth(verticalFocusPanel, 0, Unit.PCT, 0,
                Unit.PCT);
        mainPanel.animate(duration);
    }

    public void animateControlsOut(int duration) {
        cancelHiding();
        mainPanel.setWidgetBottomHeight(actionToolbar, 0, Unit.CM, 0, Unit.PX);
        mainPanel.setWidgetTopHeight(imageRasterView, 0, Unit.CM, 100, Unit.PCT);
        mainPanel.setWidgetRightWidth(imageRasterView, 0, Unit.CM, 100, Unit.PCT);
        mainPanel.setWidgetLeftWidth(treeView, 0, Unit.PCT, 0, Unit.PCT);
        mainPanel.setWidgetTopHeight(treeView, 0, Unit.PCT, 100, Unit.PCT);

        mainPanel.setWidgetBottomHeight(horizontalFocusPanel, 0, Unit.PCT, 10,
                Unit.PCT);
        mainPanel.setWidgetLeftWidth(verticalFocusPanel, 0, Unit.PCT, 10,
                Unit.PCT);
        mainPanel.animate(duration);
    }

    @Override
    public void hideControlsLater(final int duration) {
        timer.setRunnable(new Runnable() {
            @Override
            public void run() {
                animateControlsOut(1000);
            }
        });
        timer.schedule(duration);
    }

    @UiFactory
    public TreeViewImpl getView() {
        return (TreeViewImpl) treeView;
    }

    @UiFactory
    public ImageRasterViewImpl getImageRasterView() {
        return (ImageRasterViewImpl) imageRasterView;
    }

    @Override
    public void setPresenter(TagPresenter presenter) {
        this.presenter = presenter;
    }

    public String toString() {
        return "TagView:" + id;
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        log.log(Level.FINER, "On mouse over");
        cancelHiding();
        hideControlsLater(1000);
    }

    @Override
    public void cancelHiding() {
        timer.cancel();
    }

    @Override
    public void setAutoHide(boolean autoHide) {
        this.autoHide = autoHide;
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        cancelHiding();
    }

    interface TagViewImplUiBinder extends UiBinder<Widget, TagViewImpl> {
    }
}

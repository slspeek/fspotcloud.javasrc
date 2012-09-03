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
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ImageRasterView;
import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.client.main.view.api.ImageViewFactory;
import com.googlecode.fspotcloud.client.main.view.factory.ImageViewFactoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ImageRasterViewImpl extends ResizeComposite
        implements ImageRasterView,
        MouseWheelHandler {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(ImageRasterViewImpl.class.getName());
    private static ImageRasterViewImplUiBinder uiBinder = GWT.create(ImageRasterViewImplUiBinder.class);
    int storedColumnCount = 0;
    int storedRowCount = 0;
    List<ImageView> storedViews;
    @UiField
    LayoutPanel layoutPanel;
    private final ImageViewFactory imageViewFactory;
    private ImageRasterView.ImageRasterPresenter presenter;
    private final Label pagingLabel = new Label();
    private final Resources resources;

    @Inject
    public ImageRasterViewImpl(ImageViewFactoryImpl imageViewFactory,
                               Resources resources) {
        this.imageViewFactory = imageViewFactory;
        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
        layoutPanel.ensureDebugId("image-raster-view");
        layoutPanel.addDomHandler(this, MouseWheelEvent.getType());
        pagingLabel.ensureDebugId("paging-label");
        pagingLabel.setStyleName(resources.style().pagerLabel());
    }

    @Override
    public List<ImageView> buildRaster(int rowCount, int columnCount) {
        if ((rowCount == storedRowCount) && (columnCount == storedColumnCount)) {
            return storedViews;
        } else {
            layoutPanel.clear();

            List<ImageView> result = new ArrayList<ImageView>();

            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    ImageView view = imageViewFactory.get(column + "x" + row);
                    Widget asWidget = view.asWidget();
                    layoutPanel.add(asWidget);
                    layoutPanel.setWidgetTopHeight(asWidget,
                            row * (100 / (float) rowCount), Unit.PCT,
                            100 / rowCount, Unit.PCT);
                    layoutPanel.setWidgetLeftWidth(asWidget,
                            column * (100 / (float) columnCount), Unit.PCT,
                            100 / rowCount, Unit.PCT);
                    result.add(view);
                }
            }

            layoutPanel.add(pagingLabel);
            layoutPanel.setWidgetBottomHeight(pagingLabel, 0, Unit.PT, 16,
                    Unit.PT);
            layoutPanel.setWidgetRightWidth(pagingLabel, 0, Unit.PT, 10,
                    Unit.PCT);

            storedRowCount = rowCount;
            storedColumnCount = columnCount;
            storedViews = result;

            return result;
        }
    }

    @Override
    public void setPresenter(ImageRasterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onMouseWheel(MouseWheelEvent event) {
        if (event.isNorth()) {
            presenter.onMouseWheelNorth();
        } else {
            presenter.onMouseWheelSouth();
        }
    }

    @Override
    public void setPagingText(String text) {
        pagingLabel.setText(text);
    }

    interface ImageRasterViewImplUiBinder extends UiBinder<Widget, ImageRasterViewImpl> {
    }
}

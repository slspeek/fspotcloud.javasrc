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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ButtonPanelView;


public class ButtonPanelViewImpl extends LayoutPanel implements ButtonPanelView {
    private int widgetCount;
    private int currentWidget = 0;

    @Inject
    public ButtonPanelViewImpl(Resources resources) {
        setStyleName(resources.style().buttonPanelBlock());
    }

    @Override
    public void add(Widget w) {
        float step = 100f / widgetCount;
        super.add(w);
        setWidgetLeftWidth(w, currentWidget * step, Unit.PCT, step, Unit.PCT);
        //forceLayout();
        //last
        currentWidget++;
    }

    @Override
    public void setWidgetCount(int count) {
        this.widgetCount = count;
    }
}

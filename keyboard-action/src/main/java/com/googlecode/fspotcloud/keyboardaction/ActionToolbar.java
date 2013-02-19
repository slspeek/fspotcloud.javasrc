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

package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class ActionToolbar extends LayoutPanel {
    private List<Widget> widgetList = newArrayList();
    private final ActionButtonFactory actionButtonFactory;

    ActionToolbar(KeyboardActionResources keyboardActionResources,
                  ActionButtonFactory actionButtonFactory) {
        this.actionButtonFactory = actionButtonFactory;
        addStyleName(keyboardActionResources.style().buttonPanelBlock());
    }


    @Override
    public void add(Widget w) {
        widgetList.add(w);
        super.add(w);
        doLayout();
    }

    public void addCategory(ActionCategory actionCategory) {
        for (ActionUIDef actionUIDef : actionCategory.getActions()) {
            add(actionUIDef);
        }
    }

    private void doLayout() {
        final int widgetCount = widgetList.size();

        final float step = 100f / widgetCount;
        for (int i = 0; i < widgetCount; i++) {
            Widget widget = widgetList.get(i);
            setWidgetLeftWidth(widget, i * step, Unit.PCT, step, Unit.PCT);
        }
    }

    public void add(ActionUIDef actionUIDef) {
        add(actionButtonFactory.getButton(actionUIDef));
    }

    public void add(String actionId) {
        add(actionButtonFactory.getButton(actionId));
    }
}

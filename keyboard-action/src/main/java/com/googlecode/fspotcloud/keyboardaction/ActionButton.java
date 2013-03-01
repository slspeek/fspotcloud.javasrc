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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class ActionButton extends PushButton implements IActionEnableHandler, IActionDemoHandler {

    private final Logger log = Logger.getLogger(ActionButton.class.getName());
    private final ActionUIDef actionUIDef;
    private final EventBus eventBus;
    private final ActionButtonResources resources;
    ;

    ActionButton(ActionUIDef actionUIDef,
                 EventBus eventBus,
                 ActionButtonResources resources) {
        this.actionUIDef = actionUIDef;
        this.eventBus = eventBus;
        this.resources = resources;

        initialize();
    }

    private void initialize() {
        eventBus.addHandler(ActionStateEvent.TYPE, this);
        eventBus.addHandler(ActionDemoEvent.TYPE, this);
        addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                log.log(Level.FINEST, "Button: " + actionUIDef.getId() + " pressed.");
                eventBus.fireEvent(new KeyboardActionEvent(actionUIDef.getId()));
            }
        });
        setTooltip(actionUIDef.getDescription());
        setDebugId(actionUIDef.getId());
        final ImageResource imageResource = actionUIDef.getIcon();
        if (imageResource != null) {
            getUpFace().setImage(new Image(imageResource));
            setStyleName(resources.style().button());
        } else {
            addStyleName(resources.style().button());
            setCaption(actionUIDef.getName());
        }

    }

    public void setCaption(String caption) {
        setText(caption);
    }

    public void setTooltip(String tooltip) {
        asWidget().setTitle(tooltip);
    }

    public void setDebugId(String id) {
        ensureDebugId(id);
    }

    @Override
    public void onEvent(ActionStateEvent event) {
        if (event.getActionId().equals(actionUIDef.getId())) {
            setEnabled(event.getState());
            if (event.getState()) {
                String keys = event.getAcceleratorString();
                setTooltip(actionUIDef.getDescription() + " (" + keys + ")");
            }
            //setVisible(event.getState());
        }
    }

    @Override
    public void onEvent(ActionDemoEvent event) {
        if (event.getActionId().equals(actionUIDef.getId())) {

            if (event.getState()) {
                addStyleName(resources.style().in_demo());
            } else {
                removeStyleName(resources.style().in_demo());
            }
        }

    }
}

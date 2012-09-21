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
import com.google.gwt.user.client.ui.PushButton;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public class ActionButton extends PushButton implements IActionEnableHandler {

    private final Logger log = Logger.getLogger(ActionButton.class.getName());
    private final ActionDef actionDef;
    private final EventBus eventBus;

    @Inject
    public ActionButton(@Assisted ActionDef actionDef, EventBus eventBus) {
        //super(new Image(icon));
        this.actionDef = actionDef;
        this.eventBus = eventBus;
        initialize();
    }


    private void initialize() {
        eventBus.addHandler(ActionEnableEvent.TYPE, this);
        addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                log.log(Level.FINEST, "Button: " + actionDef.getId() + " pressed.");
                eventBus.fireEvent(new KeyboardActionEvent(actionDef.getId()));
            }
        });
        setCaption(actionDef.getName());
        setTooltip(actionDef.getDescription());
        setDebugId(actionDef.getId());
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
    public void onEvent(ActionEnableEvent event) {
        if (event.getActionId().equals(actionDef.getId())) {
            setEnabled(event.getState());
            //setVisible(event.getState());
        }
    }
}

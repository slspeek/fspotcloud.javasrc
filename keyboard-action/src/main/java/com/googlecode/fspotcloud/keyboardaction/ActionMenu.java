package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionMenu extends PushButton implements ClickHandler, MouseOverHandler, MouseOutHandler {

    private final Logger log = Logger.getLogger(ActionMenu.class.getName());

    private final MenuBar innerBar = new MenuBar(true);
    private final ButtonDefinitions buttonDefinitions;
    private final EventBus eventBus;
    private final PopupPanel popupPanel = new PopupPanel(true);
    private final KeyboardActionResources keyboardActionResources;
    private final ActionMenuItemSafeHtml actionMenuItemSafeHtml;
    private Timer hideTimer = new Timer() {
        @Override
        public void run() {

        }
    };

    public ActionMenu(String caption, ButtonDefinitions buttonDefinitions, EventBus eventBus, KeyboardActionResources keyboardActionResources, ActionMenuItemSafeHtml actionMenuItemSafeHtml) {
        this.buttonDefinitions = buttonDefinitions;
        this.eventBus = eventBus;
        this.keyboardActionResources = keyboardActionResources;
        this.actionMenuItemSafeHtml = actionMenuItemSafeHtml;
        addStyleName(keyboardActionResources.style().menuButton());
        popupPanel.setWidget(innerBar);
        setText(caption);
        addClickHandler(this);//outerBar.setAutoOpen(true);
        addMouseOverHandler(this);
        addMouseOutHandler(this);
        popupPanel.addStyleName(keyboardActionResources.style().popUpMenu());
        popupPanel.addAutoHidePartner(this.getElement());
        innerBar.addStyleName(keyboardActionResources.style().popUpMenu());
        innerBar.addDomHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                log.log(Level.FINEST, "Out!");
                hidePopup();
            }
        }, MouseOutEvent.getType());
        innerBar.addDomHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                log.log(Level.FINEST, "Over!");
                hideTimer.cancel();
            }
        }, MouseOverEvent.getType());
    }

    public void add(final String actionId) {
        ActionDef actionDef = buttonDefinitions.getAction(actionId);
        SafeHtml menuItemContent = actionMenuItemSafeHtml.get(actionDef);
        MenuItem menuItem = innerBar.addItem(menuItemContent, new Scheduler.ScheduledCommand() {
            @Override
            public void execute() {
                hidePopup();
                eventBus.fireEvent(new KeyboardActionEvent(actionId));
            }
        });
        menuItem.addStyleName(keyboardActionResources.style().menuItem());
    }

    @Override
    public void onClick(ClickEvent event) {
        togglePopup();
    }

    private void togglePopup() {
        if (!popupPanel.isShowing()) {
            showPopup();
        } else {
            hidePopup();
        }
    }

    private void hidePopup() {
        popupPanel.hide();
    }

    private void showPopup() {
        popupPanel.showRelativeTo(this);
        popupPanel.setPopupPosition(popupPanel.getPopupLeft() + innerBar.getOffsetWidth()/4, popupPanel.getPopupTop() - popupPanel.getOffsetHeight() - this.getOffsetHeight());
        popupPanel.show();
        innerBar.focus();
    }

    @Override
    public void onMouseOver(MouseOverEvent event) {
        log.log(Level.FINEST, "Over of the menu button!");
        showPopup();
    }

    @Override
    public void onMouseOut(MouseOutEvent event) {
        log.log(Level.FINEST, "Out of the menu button!");
        hideTimer = new Timer() {
            @Override
            public void run() {
                hidePopup();
            }
        };
        hideTimer.schedule(200);
    }
}


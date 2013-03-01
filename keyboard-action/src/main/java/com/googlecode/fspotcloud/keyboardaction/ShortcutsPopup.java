package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

import java.util.logging.Logger;

public class ShortcutsPopup extends PopupPanel {
    private final Logger log = Logger.getLogger(ShortcutsPopup.class.getName());

    private final HelpResources resources;


    @Inject
    public ShortcutsPopup(HelpResources resources) {
        this.resources = resources;
        addStyleName(resources.style().shortcutsPopup());
        setAutoHideEnabled(true);
    }

    public void setSafeHtml(SafeHtml safeHtml) {
        final HTML widget = new HTML(safeHtml);
        widget.addStyleName(resources.style().shortcutsBody());
        setWidget(widget);
    }
}

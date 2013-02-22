package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

import java.util.logging.Logger;

public class ShortCutsPopup extends PopupPanel {
    private final Logger log = Logger.getLogger(ShortCutsPopup.class.getName());

    private final KeyboardActionResources  resources;


    @Inject
    public ShortCutsPopup(KeyboardActionResources resources) {
        this.resources = resources;
        setAutoHideEnabled(true);
    }

    public void setSafeHtml(SafeHtml safeHtml) {
        final HTML widget = new HTML(safeHtml);
        widget.addStyleName(resources.style().helpBody());
        setWidget(widget);
    }
}

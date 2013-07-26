package com.googlecode.fspotcloud.keyboardaction.gwt;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.HelpResources;

import java.util.logging.Logger;

public class KeyPressMessagePopup extends PopupPanel {
    private final Logger log = Logger.getLogger(KeyPressMessagePopup.class.getName());

    private final HelpResources resources;

    private final HTML htmlBody = new HTML();

    @Inject
    public KeyPressMessagePopup(HelpResources resources) {
        this.resources = resources;
        addStyleName(resources.style().keyPressPopup());
        setWidget(htmlBody);
        setAutoHideEnabled(true);
    }

    public void setSafeHtml(SafeHtml safeHtml) {
        htmlBody.setHTML(safeHtml);
        htmlBody.addStyleName(resources.style().keyPressBody());
    }

    public void setNotFound(boolean notFound) {
        if(notFound) {
            htmlBody.addStyleName(resources.style().notFoundBody());
        } else {
            htmlBody.removeStyleName(resources.style().notFoundBody());

        }
    }
}

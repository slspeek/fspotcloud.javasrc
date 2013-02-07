package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.PushButton;

public class BigPushButton extends PushButton {
    BigPushButtonResources resources = GWT.create(BigPushButtonResources.class);

    public BigPushButton() {
        addStyleName(resources.style().button());
    }
}

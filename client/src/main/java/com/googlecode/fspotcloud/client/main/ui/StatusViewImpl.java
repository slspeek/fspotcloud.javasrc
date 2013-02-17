package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;

public class StatusViewImpl extends Label implements StatusView {

    private final AdminResources resources;

    @Inject
    public StatusViewImpl(AdminResources resources) {
        this.resources = resources;
        addStyleName(resources.style().status());
    }

    @Override
    public void setStatusText(String status) {
        setText(status);
    }
}

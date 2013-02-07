package com.googlecode.fspotcloud.client.main.ui;

import com.google.code.ginmvp.client.GinMvpDisplay;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MvpDisplay implements GinMvpDisplay {

    private final Logger log = Logger.getLogger(MvpDisplay.class.getName());
    private final DockLayoutPanel dockLayoutPanel = new DockLayoutPanel(Style.Unit.CM);
    private HasOneWidgetAdapter adapter = new HasOneWidgetAdapter(dockLayoutPanel);

    @Override
    public void setWidget(IsWidget w) {
        log.log(Level.FINEST, "set widget starting");
        adapter.setWidget(w);
        log.log(Level.FINEST, "set widget ending");
    }

    @Override
    public Widget asWidget() {
        return dockLayoutPanel;
    }
}

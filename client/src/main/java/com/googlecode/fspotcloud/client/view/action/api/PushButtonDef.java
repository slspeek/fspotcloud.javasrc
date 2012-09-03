package com.googlecode.fspotcloud.client.view.action.api;

import com.google.gwt.user.client.ui.Image;

import java.util.List;

public interface PushButtonDef {

    Image getUpImage();

    Image getUpHovering();

    Image getDownHovering();

    String getCaption();

    String getDescription();

    String getId();

    List<IGlobalShortcutController.Mode> getRelevantModes();
}

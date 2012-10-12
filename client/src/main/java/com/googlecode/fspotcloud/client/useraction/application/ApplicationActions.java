package com.googlecode.fspotcloud.client.useraction.application;

import com.google.gwt.core.client.GWT;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class ApplicationActions {

    private static final Resources RESOURCES = GWT.create(Resources.class);

    public static final ActionDef ZOOM_IN = new ActionDef("zoom-in",
            "Zoom in",
            "Zoom into the current image",
            RESOURCES.zoomInIcon());

    public static final ActionDef ZOOM_OUT = new ActionDef("zoom-out",
            "Zoom out",
            "Zoom out of the current image",
            RESOURCES.zoomOutIcon());

    public static final ActionDef TREE_FOCUS = new ActionDef("tree",
            "Focus tree",
            "Puts keyboard focus on the category tree",
            RESOURCES.treeFocusIcon());

    public static final ActionDef DASHBOARD = new ActionDef("dashboard",
            "Dashboard",
            "Go to the dashboard (admin only)",
            RESOURCES.dashboardIcon());

    public static final ActionDef ABOUT = new ActionDef("about",
            "About",
            "About this open source project",
            RESOURCES.aboutIcon());

    public static final ActionDef HIDE_CONTROLS = new ActionDef("hide-controls",
            "Hide controls",
            "Hide the button bar and the tree",
            RESOURCES.hideControlsIcon());


    public static final ActionDef LOGIN = new ActionDef("login",
            "Login",
            "Login to see more",
            RESOURCES.loginIcon());

    public static final ActionDef LOGOUT = new ActionDef("logout",
            "Logout",
            "Logout",
            RESOURCES.logoutIcon());

    public static final ActionDef SHOW_HELP = new ActionDef("help", "Help", "Show the keyboard help", RESOURCES.helpIcon());
    public static final ActionDef HIDE_HELP = new ActionDef("hide-help", "Hide help", "Hide the help popup.");
}

package com.googlecode.fspotcloud.client.useraction.application;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class ApplicationActions {

    private final Resources RESOURCES;
    public final ActionDef zoom_in;
    public final ActionDef zoom_out;
    public final ActionDef tree_focus;
    public final ActionDef dashboard;
    public final ActionDef about;
    public final ActionDef hide_controls;
    public final ActionDef login;
    public final ActionDef logout;
    public final ActionDef show_help;
    public final ActionDef hide_help;
    public final ActionDef demo;

    @Inject
    public ApplicationActions(Resources RESOURCES) {
        this.RESOURCES = RESOURCES;
        zoom_in = new ActionDef("zoom-in",
                "Zoom in",
                "Zoom into the current image",
                RESOURCES.zoomInIcon());
        zoom_out = new ActionDef("zoom-out",
                "Zoom out",
                "Zoom out of the current image",
                RESOURCES.zoomOutIcon());
        tree_focus = new ActionDef("tree",
                "Focus tree",
                "Puts keyboard focus on the category tree",
                RESOURCES.treeFocusIcon());
        dashboard = new ActionDef("dashboard",
                "Dashboard",
                "Go to the dashboard (admin only)",
                RESOURCES.dashboardIcon());
        about = new ActionDef("about",
                "About",
                "About this open source project",
                RESOURCES.aboutIcon());
        hide_controls = new ActionDef("hide-controls",
                "Hide controls",
                "Hide the button bar and the tree",
                RESOURCES.hideControlsIcon());
        login = new ActionDef("login",
                "Login",
                "Login to see more",
                RESOURCES.loginIcon());
        logout = new ActionDef("logout",
                "Logout",
                "Logout",
                RESOURCES.logoutIcon());
        show_help = new ActionDef("help", "Help", "Show the keyboard help", RESOURCES.helpIcon());
        hide_help = new ActionDef("hide-help", "Hide help", "Hide the help popup.");
        demo = new ActionDef("demo", "Demo", "Play a demo.", RESOURCES.demoIcon());
    }

}

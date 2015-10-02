package com.googlecode.fspotcloud.client.enduseraction.application;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class ApplicationActions {

	private final Resources RESOURCES;
	public final ActionUIDef zoom_in;
	public final ActionUIDef zoom_out;
	public final ActionUIDef tree_focus;
	public final ActionUIDef dashboard;
	public final ActionUIDef about;
	public final ActionUIDef hide_controls;
	public final ActionUIDef login;
	public final ActionUIDef logout;
	public final ActionUIDef show_help;
	public final ActionUIDef show_shortcuts;
	public final ActionUIDef hide_help;
	public final ActionUIDef demo;
	public final ActionUIDef reloadTree;
	public final ActionUIDef goToLatest;
	public final ActionUIDef toggleAutoHide;

	@Inject
	public ApplicationActions(Resources RESOURCES) {
		this.RESOURCES = RESOURCES;
		zoom_in = new ActionUIDef("zoom-in", "Zoom in",
				"Zoom into the current image", RESOURCES.zoomInIcon());
		zoom_out = new ActionUIDef("zoom-out", "Zoom out",
				"Zoom out of the current image", RESOURCES.zoomOutIcon());
		tree_focus = new ActionUIDef("tree", "Focus categories",
				"Puts keyboard focus on the categories",
				RESOURCES.treeFocusIcon());
		dashboard = new ActionUIDef("dashboard", "Dashboard",
				"Go to the dashboard (admin only)", RESOURCES.dashboardIcon());
		about = new ActionUIDef("about", "About",
				"About this open source project", RESOURCES.aboutIcon());
		hide_controls = new ActionUIDef("hide-controls", "Hide controls",
				"Hide the button bar and the tree",
				RESOURCES.hideControlsIcon());
		login = new ActionUIDef("login", "Login", "Login to see more",
				RESOURCES.loginIcon());
		logout = new ActionUIDef("logout", "Logout", "Logout",
				RESOURCES.logoutIcon());
		show_help = new ActionUIDef("help", "Help", "Show the keyboard help",
				RESOURCES.helpIcon());
		show_shortcuts = new ActionUIDef("shortcuts", "Shortcuts",
				"Show the shortcuts popup");
		hide_help = new ActionUIDef("hide-help", "Hide help",
				"Hide the help popup.");
		demo = new ActionUIDef("demo", "Demo", "Play a demo.",
				RESOURCES.demoIcon());
		reloadTree = new ActionUIDef("reload-tree", "Reload categories",
				"Reload categories", RESOURCES.reloadTreeIcon());
		goToLatest = new ActionUIDef("goto-latest", "Latest",
				"Go to category with latest image");
		toggleAutoHide = new ActionUIDef("auto-hide", "Auto hide",
				"Toggle auto hiding");
	}

}

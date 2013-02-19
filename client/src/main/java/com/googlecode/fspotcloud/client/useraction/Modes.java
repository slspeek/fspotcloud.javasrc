package com.googlecode.fspotcloud.client.useraction;

import com.googlecode.fspotcloud.keyboardaction.ModesProvider;

public class Modes implements ModesProvider {

    public static final String ABOUT = "ABOUT";
    public static final String SLIDESHOW = "SLIDESHOW";
    public static final String TAG_VIEW = "TAG_VIEW";
    public static final String TREE_VIEW = "TREE_VIEW";
    public static final String LOGIN = "LOGIN";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String MANAGE_GROUPS = "MANAGE_GROUPS";
    public static final String MANAGE_USERS = "MANAGE_USERS";
    public static final String EDIT_GROUP = "EDIT_GROUP";
    public static final String[] ALL_MODES = {ABOUT, SLIDESHOW,
            TAG_VIEW, TREE_VIEW,
            LOGIN, DASHBOARD,
            MANAGE_GROUPS, MANAGE_USERS, EDIT_GROUP};
    public static final String[] VIEWING_MODES = {TAG_VIEW, TREE_VIEW};

    public String[] getModes() {
        return ALL_MODES;
    }
}

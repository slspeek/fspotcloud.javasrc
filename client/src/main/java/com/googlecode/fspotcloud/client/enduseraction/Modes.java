package com.googlecode.fspotcloud.client.enduseraction;

import com.googlecode.fspotcloud.keyboardaction.ModesProvider;

public class Modes implements ModesProvider {

    public static final String ABOUT = "ABOUT";
    public static final String LOGIN = "LOGIN";
    public static final String DASHBOARD = "DASHBOARD";
    public static final String MAIL_FULLSIZE = "MAIL_FULLSIZE";
    public static final String MANAGE_GROUPS = "MANAGE_GROUPS";
    public static final String MANAGE_USERS = "MANAGE_USERS";
    public static final String MANAGE_USERS_NO_INPUT = "MANAGE_USERS_NO_INPUT";
    public static final String PASSWORD_RESET = "PASSWORD_RESET";
    public static final String PROFILE = "PROFILE";
    public static final String RESEND_EMAIL = "RESEND_EMAIL";
    public static final String EDIT_GROUP = "EDIT_GROUP";
    public static final String SLIDESHOW = "SLIDESHOW";
    public static final String SIGN_UP = "SIGN_UP";
    public static final String SEND_RESET = "SEND_RESET";
    public static final String TAG_ACCESS = "TAG_ACCESS";
    public static final String TAG_VIEW = "TAG_VIEW";
    public static final String TREE_VIEW = "TREE_VIEW";

    public static final String[] ALL_MODES =
            {
                    ABOUT,
                    TAG_VIEW,
                    TREE_VIEW,
                    LOGIN,
                    DASHBOARD,
                    MAIL_FULLSIZE,
                    MANAGE_GROUPS,
                    MANAGE_USERS,
                    MANAGE_USERS_NO_INPUT,
                    PASSWORD_RESET,
                    PROFILE,
                    RESEND_EMAIL,
                    EDIT_GROUP,
                    SLIDESHOW,
                    SEND_RESET,
                    SIGN_UP,
                    TAG_ACCESS
            };
    public static final String[] VIEWING_MODES = {TAG_VIEW, TREE_VIEW};

    public String[] getModes() {
        return ALL_MODES;
    }
}
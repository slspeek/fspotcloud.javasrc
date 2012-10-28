package com.googlecode.fspotcloud.client.useraction.navigation;

import com.google.gwt.core.client.GWT;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class NavigationActions {

    private static final Resources RESOURCES = GWT.create(Resources.class);

    public static final String HOME = "home";
    public static final ActionDef HOME_DEF = new ActionDef(HOME,
            "Home",
            "Go to the first image of the category",
            RESOURCES.homeIcon());
    public static final String PAGE_UP = "page-up";
    public static final ActionDef PAGE_UP_DEF = new ActionDef(PAGE_UP,
            "Page up",
            "Go one page back",
            RESOURCES.pageUpIcon());

    public static final String ROW_UP = "row-up";
    public static final ActionDef ROW_UP_DEF = new ActionDef(ROW_UP,
            "Row up",
            "Go one row back",
            RESOURCES.rowUpIcon());

    public static final String BACK = "back";
    public static final ActionDef BACK_DEF = new ActionDef(BACK,
            "Back",
            "Previous image in this category",
            RESOURCES.backIcon());

    public static final String NEXT = "next";
    public static final ActionDef NEXT_DEF = new ActionDef(NEXT,
            "Next",
            "Next image in this category",
            RESOURCES.nextIcon());

    public static final String ROW_DOWN = "row-down";
    public static final ActionDef ROW_DOWN_DEF = new ActionDef(ROW_DOWN,
            "Row down",
            "Go one row down",
            RESOURCES.rowDownIcon());


    public static final String PAGE_DOWN = "page-down";
    public static final ActionDef PAGE_DOWN_DEF = new ActionDef(PAGE_DOWN,
            "Page down",
            "Go one page forward",
            RESOURCES.pageDownIcon());


    public static final String END = "end";
    public static final ActionDef END_DEF = new ActionDef(END,
            "End",
            "Go to the last image of the category",
            RESOURCES.endIcon());

}

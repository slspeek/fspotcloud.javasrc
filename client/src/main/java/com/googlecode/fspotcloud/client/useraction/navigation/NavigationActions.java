package com.googlecode.fspotcloud.client.useraction.navigation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class NavigationActions {

    private final Resources RESOURCES;

    public final static String HOME_ID = "home";
    public final ActionDef home;
    public final static String PAGE_UP_ID = "page-up";
    public final ActionDef page_up;
    public final static String ROW_UP_ID = "row-up";
    public final ActionDef row_up;
    public final static String BACK_ID = "back";
    public final ActionDef back;
    public final static String NEXT_ID = "next";
    public final ActionDef next;
    public final static String ROW_DOWN_ID = "row-down";
    public final ActionDef row_down;
    public final static String PAGE_DOWN_ID = "page-down";
    public final ActionDef page_down;
    public final static String END_ID = "end";
    public final ActionDef end;
    public final ActionDef rss_feed;

    @Inject
    public NavigationActions(Resources resources) {
        RESOURCES = resources;
        end = new ActionDef(END_ID,
                "End",
                "Go to the last image of the category",
                RESOURCES.endIcon());
        page_down = new ActionDef(PAGE_DOWN_ID,
                "Page down",
                "Go one page forward",
                RESOURCES.pageDownIcon());
        row_down = new ActionDef(ROW_DOWN_ID,
                "Row down",
                "Go one row down",
                RESOURCES.rowDownIcon());
        next = new ActionDef(NEXT_ID,
                "Next",
                "Next image in this category",
                RESOURCES.nextIcon());
        back = new ActionDef(BACK_ID,
                "Back",
                "Previous image in this category",
                RESOURCES.backIcon());
        row_up = new ActionDef(ROW_UP_ID,
                "Row up",
                "Go one row back",
                RESOURCES.rowUpIcon());
        page_up = new ActionDef(PAGE_UP_ID,
                "Page up",
                "Go one page back",
                RESOURCES.pageUpIcon());
        home = new ActionDef(HOME_ID,
                "Home",
                "Go to the first image of the category",
                RESOURCES.homeIcon());
        rss_feed = new ActionDef("rss_feed",
                "RSS-feed",
                "The RSS-feed for this category",
                RESOURCES.rssReedIcon());
    }
}

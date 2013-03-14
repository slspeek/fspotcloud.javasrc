package com.googlecode.fspotcloud.client.enduseraction.navigation;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class NavigationActions {

    private final Resources RESOURCES;

    public final static String HOME_ID = "home";
    public final ActionUIDef home;
    public final static String PAGE_UP_ID = "page-up";
    public final ActionUIDef page_up;
    public final static String ROW_UP_ID = "row-up";
    public final ActionUIDef row_up;
    public final static String BACK_ID = "back";
    public final ActionUIDef back;
    public final static String NEXT_ID = "next";
    public final ActionUIDef next;
    public final static String ROW_DOWN_ID = "row-down";
    public final ActionUIDef row_down;
    public final static String PAGE_DOWN_ID = "page-down";
    public final ActionUIDef page_down;
    public final static String END_ID = "end";
    public final ActionUIDef end;
    public final ActionUIDef all_photos;
    public final ActionUIDef rss_feed;

    @Inject
    public NavigationActions(Resources resources) {
        RESOURCES = resources;
        end = new ActionUIDef(END_ID,
                "End",
                "Go to the last image of the category",
                RESOURCES.endIcon());
        page_down = new ActionUIDef(PAGE_DOWN_ID,
                "Page down",
                "Go one page forward",
                RESOURCES.pageDownIcon());
        row_down = new ActionUIDef(ROW_DOWN_ID,
                "Row down",
                "Go one row down",
                RESOURCES.rowDownIcon());
        next = new ActionUIDef(NEXT_ID,
                "Next",
                "Next image in this category",
                RESOURCES.nextIcon());
        back = new ActionUIDef(BACK_ID,
                "Back",
                "Previous image in this category",
                RESOURCES.backIcon());
        row_up = new ActionUIDef(ROW_UP_ID,
                "Row up",
                "Go one row back",
                RESOURCES.rowUpIcon());
        page_up = new ActionUIDef(PAGE_UP_ID,
                "Page up",
                "Go one page back",
                RESOURCES.pageUpIcon());
        home = new ActionUIDef(HOME_ID,
                "Home",
                "Go to the first image of the category",
                RESOURCES.homeIcon());
        rss_feed = new ActionUIDef("rss_feed",
                "RSS-feed",
                "The RSS-feed for this category",
                RESOURCES.rssReedIcon());
        all_photos = new ActionUIDef("all-photos", "All", "View all images");
    }
}

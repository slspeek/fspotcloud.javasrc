package com.googlecode.fspotcloud.client.useraction.raster;

import com.google.gwt.core.client.GWT;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class RasterActions {

    private static final Resources RESOURCES = GWT.create(Resources.class);

    public static final ActionDef ADD_COLUMN = new ActionDef("add-colum",
            "Add column",
            "Adds one column to raster",
            RESOURCES.addColumnIcon());
    public static final ActionDef ADD_ROW = new ActionDef("add-row",
            "Add row",
            "Adds one row to raster",
            RESOURCES.addRowIcon());

    public static final ActionDef REMOVE_COLUMN = new ActionDef("remove-column",
            "Remove column",
            "Removes one column from the raster",
            RESOURCES.removeColumnIcon());

    public static final ActionDef REMOVE_ROW = new ActionDef("remove-row",
            "Remove row",
            "Removes one row from the raster",
            RESOURCES.removeRowIcon());

    public static final ActionDef SET_DEFAULT_RASTER = new ActionDef("reset",
            "Reset raster",
            "Resets raster to defaults",
            RESOURCES.resetIcon());

    public static final ActionDef TOGGLE_TABULAR_VIEW = new ActionDef("raster",
            "Toggle raster",
            "Toggle tabular viewing",
            RESOURCES.tabularIcon());

    public static final ActionDef SET_RASTER_2x2 = new ActionDef("2x2",
            "2x2",
            "Sets the raster to 2 x 2",
            RESOURCES.icon2x2());

    public static final ActionDef SET_RASTER_3x3 = new ActionDef("3x3",
            "3x3",
            "Sets the raster to 3 x 3",
            RESOURCES.icon3x3());

    public static final ActionDef SET_RASTER_4x4 = new ActionDef("4x4",
            "4x4",
            "Sets the raster to 4 x 4",
            RESOURCES.icon4x4());

    public static final ActionDef SET_RASTER_5x5 = new ActionDef("5x5",
            "5x5",
            "Sets the raster to 5 x 5",
            RESOURCES.icon5x5());

    public static final ActionDef MAIL_FULLSIZE = new ActionDef("mail-fullsize",
            "Mail fullsize",
            "Mail the high resolution version of the image to you",
            RESOURCES.emailIcon());
}

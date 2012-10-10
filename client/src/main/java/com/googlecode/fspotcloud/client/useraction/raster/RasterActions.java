package com.googlecode.fspotcloud.client.useraction.raster;

import com.google.gwt.core.client.GWT;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class RasterActions {

    private static final Resources RESOURCES = GWT.create(Resources.class);
    private static final SetRasterActionFactory setRasterActionFactory = new SetRasterActionFactory();

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

    public static final ActionDef SET_RASTER_2x2 = setRasterActionFactory.getSetRasterTo(2,2);

    public static final ActionDef SET_RASTER_3x3 = setRasterActionFactory.getSetRasterTo(3, 3);

    public static final ActionDef SET_RASTER_4x4 = setRasterActionFactory.getSetRasterTo(4, 4);

    public static final ActionDef SET_RASTER_5x5 = setRasterActionFactory.getSetRasterTo(5, 5);
    public static final ActionDef SET_RASTER_6x6 = setRasterActionFactory.getSetRasterTo(6, 6);

    public static final ActionDef MAIL_FULLSIZE = new ActionDef("mail-fullsize",
            "Mail fullsize",
            "Mail the high resolution version of the image to you",
            RESOURCES.emailIcon());
}

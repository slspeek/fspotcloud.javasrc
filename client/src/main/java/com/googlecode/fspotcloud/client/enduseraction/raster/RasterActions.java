package com.googlecode.fspotcloud.client.enduseraction.raster;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class RasterActions {

    private final Resources RESOURCES;
    private final SetRasterActionFactory setRasterActionFactory;

    public final ActionUIDef add_column;
    public final ActionUIDef add_row;
    public final ActionUIDef remove_column;
    public final ActionUIDef remove_row;
    public final ActionUIDef set_default_raster;
    public final ActionUIDef toggle_tabular_view;
    public final ActionUIDef set_raster_2x2;
    public final ActionUIDef set_raster_3x3;
    public final ActionUIDef set_raster_4x4;
    public final ActionUIDef set_raster_5x5;
    public final ActionUIDef set_raster_6x6;
    public final ActionUIDef mail_fullsize;

    @Inject
    public RasterActions(Resources resources, SetRasterActionFactory setRasterActionFactory) {
        RESOURCES = resources;
        this.setRasterActionFactory = setRasterActionFactory;
        mail_fullsize = new ActionUIDef("mail-fullsize",
                "Mail fullsize",
                "Mail the high resolution version of the image to you",
                RESOURCES.emailIcon());
        set_raster_6x6 = this.setRasterActionFactory.getSetRasterTo(6, 6);
        set_raster_5x5 = this.setRasterActionFactory.getSetRasterTo(5, 5);
        set_raster_4x4 = this.setRasterActionFactory.getSetRasterTo(4, 4);
        set_raster_3x3 = this.setRasterActionFactory.getSetRasterTo(3, 3);
        set_raster_2x2 = this.setRasterActionFactory.getSetRasterTo(2, 2);
        toggle_tabular_view = new ActionUIDef("raster",
                "Toggle raster",
                "Toggle tabular viewing",
                RESOURCES.tabularIcon());
        set_default_raster = new ActionUIDef("reset",
                "Reset raster",
                "Resets raster to defaults",
                RESOURCES.resetIcon());
        remove_row = new ActionUIDef("remove-row",
                "Remove row",
                "Removes one row from the raster",
                RESOURCES.removeRowIcon());
        remove_column = new ActionUIDef("remove-column",
                "Remove column",
                "Removes one column from the raster",
                RESOURCES.removeColumnIcon());
        add_row = new ActionUIDef("add-row",
                "Add row",
                "Adds one row to raster",
                RESOURCES.addRowIcon());
        add_column = new ActionUIDef("add-column",
                "Add column",
                "Adds one column to raster",
                RESOURCES.addColumnIcon());
    }
}

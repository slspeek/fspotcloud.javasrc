package com.googlecode.fspotcloud.client.useraction.raster;

import com.googlecode.fspotcloud.keyboardaction.ActionDef;

public class SetRasterActionFactory {

    public ActionDef getSetRasterTo(int w, int h) {
        return new ActionDef(getId(w,h), getName(w,h), getDescription(w,h) );
    }

    private String getDescription(int w, int h) {

        return "Sets to image raster to " + w + " x " + h + ".";
    }

    private String getName(int w, int h) {
        return getId(w,h);
    }

    private String getId(int w, int h) {
        return w + "x" + h;
    }
}

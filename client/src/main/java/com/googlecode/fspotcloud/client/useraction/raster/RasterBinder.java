package com.googlecode.fspotcloud.client.useraction.raster;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;

public class RasterBinder extends AbstractBinder {


    @Inject
    public RasterBinder(
            CategoryDef categoryDef) {
        super(categoryDef.NAVIGATION);

    }


    @Override
    public void build() {

    }

}

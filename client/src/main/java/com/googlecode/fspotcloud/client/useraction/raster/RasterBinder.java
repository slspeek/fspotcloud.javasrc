package com.googlecode.fspotcloud.client.useraction.raster;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.raster.handler.SetRasterHandlerFactory;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

import java.security.KeyStore;

public class RasterBinder extends AbstractBinder {

    private final SetRasterHandlerFactory setRasterHandlerFactory;


    @Inject
    public RasterBinder(
            CategoryDef categoryDef, SetRasterHandlerFactory setRasterHandlerFactory) {
        super(categoryDef.RASTER);
        this.setRasterHandlerFactory = setRasterHandlerFactory;
    }


    @Override
    public void build() {
        bind(RasterActions.SET_RASTER_2x2, setRasterHandlerFactory.get(2), getKey('2'));
        bind(RasterActions.SET_RASTER_3x3, setRasterHandlerFactory.get(3), getKey('3'));
        bind(RasterActions.SET_RASTER_4x4, setRasterHandlerFactory.get(4), getKey('4'));
        bind(RasterActions.SET_RASTER_5x5, setRasterHandlerFactory.get(5), getKey('5'));
        bind(RasterActions.SET_RASTER_6x6, setRasterHandlerFactory.get(6), getKey('6'));
    }

    private KeyboardBinding getKey(char i) {
        return KeyboardBinding.bind(new KeyStroke(i)).withDefaultModes(Modes.TAG_VIEW, Modes.TREE_VIEW);
    }


}

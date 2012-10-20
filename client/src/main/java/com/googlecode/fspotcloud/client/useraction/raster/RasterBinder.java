package com.googlecode.fspotcloud.client.useraction.raster;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.raster.handler.*;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

public class RasterBinder extends AbstractBinder {

    private final SetRasterHandlerFactory setRasterHandlerFactory;
    private final AddColumnHandler addColumnHandler;
    private final RemoveColumnHandler removeColumnHandler;
    private final AddRowHandler addRowHandler;
    private final RemoveRowHandler removeRowHandler;
    private final MailFullSizeHandler mailFullSizeHandler;
    private final ResetRasterHandler resetRasterHandler;
    private final ToggleTabularViewHandler toggleTabularViewHandler;

    @Inject
    public RasterBinder(
            CategoryDef categoryDef,
            SetRasterHandlerFactory setRasterHandlerFactory,
            AddColumnHandler addColumnHandler,
            RemoveColumnHandler removeColumnHandler,
            AddRowHandler addRowHandler,
            RemoveRowHandler removeRowHandler,
            MailFullSizeHandler mailFullSizeHandler,
            ResetRasterHandler resetRasterHandler,
            ToggleTabularViewHandler toggleTabularViewHandler) {
        super(categoryDef.RASTER);
        this.setRasterHandlerFactory = setRasterHandlerFactory;
        this.addColumnHandler = addColumnHandler;
        this.removeColumnHandler = removeColumnHandler;
        this.addRowHandler = addRowHandler;
        this.removeRowHandler = removeRowHandler;
        this.mailFullSizeHandler = mailFullSizeHandler;
        this.resetRasterHandler = resetRasterHandler;
        this.toggleTabularViewHandler = toggleTabularViewHandler;
    }

    @Override
    public void build() {
        bind(RasterActions.ADD_COLUMN, addColumnHandler, getKey('Z'));
        bind(RasterActions.REMOVE_COLUMN, removeColumnHandler, getKey('X'));
        bind(RasterActions.ADD_ROW, addRowHandler, getKey('C'));
        bind(RasterActions.REMOVE_ROW, removeRowHandler, getKey('V'));
        bind(RasterActions.MAIL_FULLSIZE, mailFullSizeHandler, getKey('M'));
        bind(RasterActions.SET_DEFAULT_RASTER, resetRasterHandler, getKey('0'));
        bind(RasterActions.TOGGLE_TABULAR_VIEW, toggleTabularViewHandler, getKey('1'));


        bind(RasterActions.SET_RASTER_2x2, setRasterHandlerFactory.get(2), getKey('2'));
        bind(RasterActions.SET_RASTER_3x3, setRasterHandlerFactory.get(3), getKey('3'));
        bind(RasterActions.SET_RASTER_4x4, setRasterHandlerFactory.get(4), getKey('4'));
        bind(RasterActions.SET_RASTER_5x5, setRasterHandlerFactory.get(5), getKey('5'));
        bind(RasterActions.SET_RASTER_6x6, setRasterHandlerFactory.get(6), getKey('6'));
    }

    private KeyboardBinding getKey(char i) {
        return KeyboardBinding.bind(new KeyStroke(i)).withDefaultModes(Modes.VIEWING_MODES);
    }

}

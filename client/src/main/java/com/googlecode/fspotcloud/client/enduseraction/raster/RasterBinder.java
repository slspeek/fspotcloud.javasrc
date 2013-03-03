package com.googlecode.fspotcloud.client.enduseraction.raster;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.Modes;
import com.googlecode.fspotcloud.client.enduseraction.raster.handler.*;
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
    private final RasterActions actions;

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
            ToggleTabularViewHandler toggleTabularViewHandler,
            RasterActions actions) {
        super(categoryDef.RASTER);
        this.setRasterHandlerFactory = setRasterHandlerFactory;
        this.addColumnHandler = addColumnHandler;
        this.removeColumnHandler = removeColumnHandler;
        this.addRowHandler = addRowHandler;
        this.removeRowHandler = removeRowHandler;
        this.mailFullSizeHandler = mailFullSizeHandler;
        this.resetRasterHandler = resetRasterHandler;
        this.toggleTabularViewHandler = toggleTabularViewHandler;
        this.actions = actions;
    }

    @Override
    public void build() {
        bind(actions.add_column, addColumnHandler, getKey('Z'));
        bind(actions.remove_column, removeColumnHandler, getKey('X'));
        bind(actions.add_row, addRowHandler, getKey('C'));
        bind(actions.remove_row, removeRowHandler, getKey('V'));
        bind(actions.mail_fullsize, mailFullSizeHandler, getKey('M'));
        bind(actions.set_default_raster, resetRasterHandler, getKey('0'));
        KeyboardBinding binding = KeyboardBinding.bind(KeyStroke.SPACE, new KeyStroke('1')).withDefaultModes(Modes.TAG_VIEW);
        bind(actions.toggle_tabular_view, toggleTabularViewHandler, binding);
        bind(actions.set_raster_2x2, setRasterHandlerFactory.get(2), getKey('2'));
        bind(actions.set_raster_3x3, setRasterHandlerFactory.get(3), getKey('3'));
        bind(actions.set_raster_4x4, setRasterHandlerFactory.get(4), getKey('4'));
        bind(actions.set_raster_5x5, setRasterHandlerFactory.get(5), getKey('5'));
        bind(actions.set_raster_6x6, setRasterHandlerFactory.get(6), getKey('6'));
    }

    private KeyboardBinding getKey(char i) {
        return KeyboardBinding.bind(new KeyStroke(i)).withDefaultModes(Modes.VIEWING_MODES);
    }

}

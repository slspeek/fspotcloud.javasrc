package com.googlecode.fspotcloud.client.useraction.raster.handler;

import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public interface SetRasterHandlerFactory {
    SetRasterHandler get(int size);
}

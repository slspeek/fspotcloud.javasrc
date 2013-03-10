package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class PlaceContextProvider implements Provider<PlaceContext> {

    @Inject
    private IModeController modeController;
    @Inject
    private PlaceController placeController;


    @Override
    public PlaceContext get() {
        return new PlaceContext(placeController.getWhere().getClass(), modeController.getFlags());
    }
}

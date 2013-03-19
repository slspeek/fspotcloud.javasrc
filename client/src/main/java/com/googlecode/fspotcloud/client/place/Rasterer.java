package com.googlecode.fspotcloud.client.place;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.IRasterer;

public class Rasterer implements IRasterer {

    private final PlaceManager placeManager;
    private final IPlaceController placeController;
    private final RasterState rasterState;

    @Inject
    private Rasterer(PlaceManager placeManager,
                     IPlaceController placeController,
                     RasterState rasterState) {
        this.placeManager = placeManager;
        this.placeController = placeController;
        this.rasterState = rasterState;
    }

    @Override
    public void increaseRasterWidth(int amount) {
        rasterState.increaseColumnCount(amount);
        reloadCurrentPlaceOnNewSize();
    }

    @Override
    public void increaseRasterHeight(int amount) {
        rasterState.increaseRowCount(amount);
        reloadCurrentPlaceOnNewSize();
    }

    @Override
    public void setRasterDimension(int i, int j) {
        rasterState.setColumnCount(i);
        rasterState.setRowCount(j);
        reloadCurrentPlaceOnNewSize();
    }

    private void reloadCurrentPlaceOnNewSize() {
        BasePlace now = placeController.where();
        BasePlace destination = placeManager.getTabularPlace(now);
        placeController.goTo(destination);
    }

    @Override
    public void resetRasterSize() {
        rasterState.reset();
        reloadCurrentPlaceOnNewSize();
    }
}

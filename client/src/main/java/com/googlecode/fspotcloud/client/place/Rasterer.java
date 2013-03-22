package com.googlecode.fspotcloud.client.place;

import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.IRasterer;
import com.googlecode.fspotcloud.client.place.api.Navigator;

public class Rasterer implements IRasterer {

    private final IPlaceController placeController;
    private final RasterState rasterState;

    @Inject
    private Rasterer(
                     IPlaceController placeController,
                     RasterState rasterState) {
        this.placeController = placeController;
        this.rasterState = rasterState;
    }

    private BasePlace getTabularPlace(BasePlace place) {
        return getResizedPlace(place,
                rasterState.getColumnCount(),
                rasterState.getRowCount());
    }

    private BasePlace getResizedPlace(BasePlace place, int width, int height) {
        PlaceBuilder converter = new PlaceBuilder(place);
        converter.setColumnCount(width);
        converter.setRowCount(height);
        return converter.place();
    }

    @Override
    public void goOneByOne() {
        placeController.goTo(getOneByOne());
    }

    private BasePlace getOneByOne() {
        return getOneByOne(placeController.where());
    }

    private BasePlace getOneByOne(BasePlace place) {
        PlaceBuilder converter = new PlaceBuilder(place);
        return converter.setRowCount(1).setColumnCount(1).place();
    }

    @Override
    public void toggleRasterView() {
        placeController.goTo(toggleRasterView(placeController.where()));
    }

    private BasePlace toggleRasterView(BasePlace place) {
        int width = place.getColumnCount();
        int height = place.getRowCount();
        if ((width * height) > 1) {
            return getOneByOne(place);
        } else {
            return getTabularPlace(place);
        }
    }

    @Override
    public void toggleZoomView(String tagId,
                                    String photoId) {
        BasePlace place = placeController.where();
        PlaceBuilder converter = new PlaceBuilder(place);
        converter.setTagId(tagId);
        converter.setPhotoId(photoId);
        int width = place.getColumnCount();
        int height = place.getRowCount();
        if ((width * height) > 1) {
            // Zoom in
            width = 1;
            height = 1;
        } else {
            // Zoom out
            width = rasterState.getColumnCount();
            height = rasterState.getRowCount();
        }
        converter.setColumnCount(width);
        converter.setRowCount(height);
        placeController.goTo(converter.place());
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
        BasePlace destination = getTabularPlace(now);
        placeController.goTo(destination);
    }

    @Override
    public void resetRasterSize() {
        rasterState.reset();
        reloadCurrentPlaceOnNewSize();
    }


    @Override
    public void zoom(Navigator.Zoom direction) {
        BasePlace now = placeController.where();
        BasePlace destination = zoom(now, direction);
        placeController.goTo(destination);
    }


    BasePlace zoom(BasePlace now, Navigator.Zoom direction) {
        BasePlace dest;
        if (direction == Navigator.Zoom.IN) {
            int width = now.getColumnCount();
            int height = now.getRowCount();

            if ((width * height) == 1) {
                //cannot zoom further IN
                dest = now;
            } else {
                //try decreasing both width and height
                final int newWidth = width - 1;
                rasterState.setRowCount(height - 1);
                rasterState.setColumnCount(newWidth);


                if (newWidth != rasterState.getColumnCount()) {
                    //width was not decreased
                    // switch to 1x1
                    dest = getOneByOne(now);
                } else {
                    dest = getTabularPlace(now);
                }
            }
        } else {
            //Zooming OUT
            int width = now.getColumnCount();
            int height = now.getRowCount();
            rasterState.setColumnCount(width + 1);
            rasterState.setRowCount(height + 1);
            dest = getTabularPlace(now);
        }
        return dest;
    }


}

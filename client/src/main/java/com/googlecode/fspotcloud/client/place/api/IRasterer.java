package com.googlecode.fspotcloud.client.place.api;

public interface IRasterer {

    void increaseRasterWidth(int amount);

    void increaseRasterHeight(int amount);

    void setRasterDimension(int i, int j);

    void resetRasterSize();

    void zoom(Navigator.Zoom direction);

    void goOneByOne();

    void toggleRasterView();

    void toggleZoomView(String tagId, String photoId);
}

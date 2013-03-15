package com.googlecode.fspotcloud.client.place.api;

public interface IRasterer {
    void setRasterWidth(int width);

    void setRasterHeight(int height);

    void increaseRasterWidth(int amount);

    void increaseRasterHeight(int amount);

    void toggleRasterView();

    void setRasterDimension(int i, int j);

    void resetRasterSize();
}

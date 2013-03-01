package com.googlecode.fspotcloud.client.main.view.factory;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.view.api.ImageView;
import com.googlecode.fspotcloud.client.main.view.api.ImageViewFactory;

@GwtCompatible
public class SingleImageViewProvider implements Provider<ImageView> {

    private final ImageViewFactory imageViewFactory;

    @Inject
    public SingleImageViewProvider(ImageViewFactory imageViewFactory) {
        this.imageViewFactory = imageViewFactory;
    }

    @Override
    public ImageView get() {
        return imageViewFactory.get("slideshow");
    }
}

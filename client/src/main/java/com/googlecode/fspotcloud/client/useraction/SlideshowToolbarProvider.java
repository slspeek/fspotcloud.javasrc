package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowView;
import com.googlecode.fspotcloud.client.useraction.slideshow.SlideshowActions;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class SlideshowToolbarProvider implements Provider<ActionToolbar> {
    private final KeyboardActionFactory keyboardActionFactory;
    private final SlideshowView.SlideshowPresenter slideshowPresenter;

    @Inject
    public SlideshowToolbarProvider(UserActionFactory actionFactory,
                                    KeyboardActionFactory keyboardActionFactory,
                                    SlideshowView.SlideshowPresenter slideshowPresenter) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.slideshowPresenter = slideshowPresenter;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();

        toolbar.add(SlideshowActions.SLIDESHOW_START);
        toolbar.add(SlideshowActions.SLIDESHOW_PAUSE);
        toolbar.add(SlideshowActions.SLIDESHOW_STOP);
        toolbar.add(SlideshowActions.SLIDESHOW_SLOWER);
        toolbar.add(slideshowPresenter.getView().asWidget());
        toolbar.add(SlideshowActions.SLIDESHOW_FASTER);

        return toolbar;
    }
}

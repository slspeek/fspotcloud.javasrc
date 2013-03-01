package com.googlecode.fspotcloud.client.enduseraction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.enduseraction.slideshow.SlideshowActions;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowDelayView;
import com.googlecode.fspotcloud.keyboardaction.ActionToolbar;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;

public class SlideshowToolbarProvider implements Provider<ActionToolbar> {
    private final KeyboardActionFactory keyboardActionFactory;
    private final SlideshowDelayView.SlideshowPresenter slideshowPresenter;
    private final SlideshowActions actions;

    @Inject
    public SlideshowToolbarProvider(KeyboardActionFactory keyboardActionFactory,
                                    SlideshowDelayView.SlideshowPresenter slideshowPresenter,
                                    SlideshowActions actions) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.slideshowPresenter = slideshowPresenter;
        this.actions = actions;
    }


    @Override
    public ActionToolbar get() {
        ActionToolbar toolbar = keyboardActionFactory.getToolBar();

        toolbar.add(actions.slideshow_start);
        toolbar.add(actions.slideshow_pause);
        toolbar.add(actions.slideshow_stop);
        toolbar.add(actions.slideshow_slower);
        toolbar.add(slideshowPresenter.getView().asWidget());
        toolbar.add(actions.slideshow_faster);

        return toolbar;
    }
}

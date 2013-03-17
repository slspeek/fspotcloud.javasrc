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
    private final ActionToolbar toolbar;

    @Inject
    public SlideshowToolbarProvider(KeyboardActionFactory keyboardActionFactory,
                                    SlideshowDelayView.SlideshowPresenter slideshowPresenter,
                                    SlideshowActions actions, ActionToolbar toolbar) {
        this.keyboardActionFactory = keyboardActionFactory;
        this.slideshowPresenter = slideshowPresenter;
        this.actions = actions;
        this.toolbar = toolbar;
    }


    @Override
    public ActionToolbar get() {

        toolbar.add(actions.slideshow_start);
        toolbar.add(actions.slideshow_pause);
        toolbar.add(actions.slideshow_stop);
        toolbar.add(actions.slideshow_slower);
        toolbar.add(slideshowPresenter.getView().asWidget());
        toolbar.add(actions.slideshow_faster);

        return toolbar;
    }
}

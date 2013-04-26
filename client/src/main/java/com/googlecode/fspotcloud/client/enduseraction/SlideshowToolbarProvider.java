package com.googlecode.fspotcloud.client.enduseraction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.enduseraction.slideshow.SlideshowActions;
import com.googlecode.fspotcloud.client.main.ui.SlideshowToolbarButtonResources;
import com.googlecode.fspotcloud.client.main.ui.SlideshowToolbarResources;
import com.googlecode.fspotcloud.client.main.ui.ToolbarButtonResources;
import com.googlecode.fspotcloud.client.main.view.api.SlideshowDelayView;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionFactory;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbar;

public class SlideshowToolbarProvider implements Provider<ActionToolbar> {
    private final SlideshowDelayView.SlideshowPresenter slideshowPresenter;
    private final SlideshowActions actions;
    private final ActionToolbar toolbar;
    private final SlideshowToolbarButtonResources slideshowToolbarButtonResources;

    @Inject
    public SlideshowToolbarProvider(KeyboardActionFactory keyboardActionFactory,
                                    SlideshowDelayView.SlideshowPresenter slideshowPresenter,
                                    SlideshowActions actions,
                                    SlideshowToolbarResources slideshowToolbarResources,
                                    SlideshowToolbarButtonResources slideshowToolbarButtonResources) {
        this.slideshowPresenter = slideshowPresenter;
        this.actions = actions;
        this.slideshowToolbarButtonResources = slideshowToolbarButtonResources;
        this.toolbar = keyboardActionFactory.getToolBar(slideshowToolbarResources);
        //this.toolbar = keyboardActionFactory.getToolBar();
    }


    @Override
    public ActionToolbar get() {
        toolbar.setActionButtonResources(slideshowToolbarButtonResources);
        toolbar.setButtonStylePrimaryName("slideshow-toolbar-button");
        toolbar.add(actions.slideshow_start);
        toolbar.add(actions.slideshow_pause);
        toolbar.add(actions.slideshow_stop);
        toolbar.add(actions.slideshow_slower);
        toolbar.add(slideshowPresenter.getView().asWidget());
        toolbar.add(actions.slideshow_faster);

        return toolbar;
    }
}

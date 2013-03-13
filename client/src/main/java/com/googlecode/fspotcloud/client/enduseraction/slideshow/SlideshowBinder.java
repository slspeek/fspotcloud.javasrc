package com.googlecode.fspotcloud.client.enduseraction.slideshow;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.AbstractBinder;
import com.googlecode.fspotcloud.client.enduseraction.CategoryDef;
import com.googlecode.fspotcloud.client.enduseraction.slideshow.handler.*;
import com.googlecode.fspotcloud.client.place.BasePlace;
import com.googlecode.fspotcloud.client.place.SlideshowPlace;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.Relevance;

public class SlideshowBinder extends AbstractBinder {

    private final FasterHandler fasterHandler;
    private final PauseHandler pauseHandler;
    private final SlowerHandler slowerHandler;
    private final StartHandler startHandler;
    private final StopHandler stopHandler;
    private final SlideshowActions actions;

    @Inject
    public SlideshowBinder(
            CategoryDef categoryDef,
            FasterHandler fasterHandler,
            PauseHandler pauseHandler,
            SlowerHandler slowerHandler,
            StartHandler startHandler,
            StopHandler stopHandler,
            SlideshowActions actions) {
        super(categoryDef.SLIDESHOW);
        this.fasterHandler = fasterHandler;
        this.pauseHandler = pauseHandler;
        this.slowerHandler = slowerHandler;
        this.startHandler = startHandler;
        this.stopHandler = stopHandler;
        this.actions = actions;
    }

    @Override
    public void build() {
        Relevance binding;
        bind(actions.slideshow_stop, stopHandler, get('Q'));
        bind(actions.slideshow_faster, fasterHandler, get('M'));
        bind(actions.slideshow_slower, slowerHandler, get('N'));
        binding = new Relevance(SlideshowPlace.class, BasePlace.class).addDefaultKeys(new KeyStroke('S'));
        bind(actions.slideshow_start, startHandler, binding);
        bind(actions.slideshow_pause, pauseHandler, get(KeyStroke.KEY_SPACE));
    }

    private Relevance get(int code) {
        return new Relevance(SlideshowPlace.class).addDefaultKeys(new KeyStroke(code));
    }

}

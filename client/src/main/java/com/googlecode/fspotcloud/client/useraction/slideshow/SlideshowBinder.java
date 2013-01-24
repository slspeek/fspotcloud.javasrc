package com.googlecode.fspotcloud.client.useraction.slideshow;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.slideshow.handler.*;
import com.googlecode.fspotcloud.keyboardaction.KeyStroke;
import com.googlecode.fspotcloud.keyboardaction.KeyboardBinding;

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
        KeyboardBinding binding;
        bind(actions.slideshow_stop, stopHandler, get('Q'));
        bind(actions.slideshow_faster, fasterHandler, get('M'));
        bind(actions.slideshow_slower, slowerHandler, get('N'));
        binding = KeyboardBinding.bind(new KeyStroke('S')).withDefaultModes(Modes.SLIDESHOW, Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(actions.slideshow_start, startHandler, binding);
        bind(actions.slideshow_pause, pauseHandler, get(KeyStroke.KEY_SPACE));
    }

    private KeyboardBinding get(int code) {
        return KeyboardBinding.bind(new KeyStroke(code)).withDefaultModes(Modes.SLIDESHOW);
    }

}

package com.googlecode.fspotcloud.client.useraction.slideshow;

import com.google.gwt.event.dom.client.KeyCodes;
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



    @Inject
    public SlideshowBinder(
            CategoryDef categoryDef,
            FasterHandler fasterHandler,
            PauseHandler pauseHandler,
            SlowerHandler slowerHandler,
            StartHandler startHandler,
            StopHandler stopHandler) {
        super(categoryDef.SLIDESHOW);
        this.fasterHandler = fasterHandler;
        this.pauseHandler = pauseHandler;
        this.slowerHandler = slowerHandler;
        this.startHandler = startHandler;
        this.stopHandler = stopHandler;
    }


    @Override
    public void build() {
        KeyboardBinding binding = KeyboardBinding.bind(new KeyStroke('Q')).withDefaultModes(Modes.SLIDESHOW);
        bind(SlideshowActions.SLIDESHOW__END, stopHandler, binding);

        binding = KeyboardBinding.bind(new KeyStroke('M')).withDefaultModes(Modes.SLIDESHOW);
        bind(SlideshowActions.SLIDESHOW_FASTER, fasterHandler, binding);

        binding = KeyboardBinding.bind(new KeyStroke('N')).withDefaultModes(Modes.SLIDESHOW);
        bind(SlideshowActions.SLIDESHOW_SLOWER, slowerHandler, binding);

        binding = KeyboardBinding.bind(new KeyStroke('S')).withDefaultModes(Modes.SLIDESHOW, Modes.TAG_VIEW, Modes.TREE_VIEW);
        bind(SlideshowActions.SLIDESHOW_START, startHandler, binding);

        binding = KeyboardBinding.bind(new KeyStroke(KeyStroke.KEY_SPACE)).withDefaultModes(Modes.SLIDESHOW);
        bind(SlideshowActions.SLIDESHOW_PAUSE, pauseHandler, binding);

    }

}

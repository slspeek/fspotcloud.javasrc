/*
 * Copyright 2010-2012 Steven L. Speek.
 * This program is free software; you can redistribute it
                and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free
                Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is
                distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied
                warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public
                License for more details.
 * You should have received a copy of the GNU General Public License
 * along
                with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330,
                Boston, MA 02111-1307, USA.
 *
 */

package com.googlecode.fspotcloud.client.place;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.shared.SlideshowStatusEvent;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Navigator.Direction;
import com.googlecode.fspotcloud.client.place.api.Navigator.Unit;
import com.googlecode.fspotcloud.client.place.api.Slideshow;

import java.util.logging.Logger;


public class SlideshowImpl implements Slideshow {
    private final Logger log = Logger.getLogger(SlideshowImpl.class.getName());
    private final Navigator navigator;
    private final TimerInterface timer;
    private boolean isRunning = false;
    private final float INCREASE_FACTOR = 4f / 3f;
    private float delay = 4f;
    private final EventBus eventBus;

    @Inject
    public SlideshowImpl(Navigator navigator, TimerInterface timer,
                         EventBus eventBus) {
        this.eventBus = eventBus;
        this.navigator = navigator;
        this.timer = timer;
        initTimer();
        log.info("Created");
    }

    private void initTimer() {
        timer.setRunnable(new Runnable() {
            @Override
            public void run() {
                navigator.canGoAsync(Direction.FORWARD, Unit.SINGLE,
                        new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught) {
                                log.warning(caught.getMessage());
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                go(result);
                            }
                        });
            }
        });
    }

    private void go(boolean canGo) {
        if (canGo) {
            navigator.goAsync(Direction.FORWARD, Unit.SINGLE);
        } else {
            stop();
            log.info("Timer stopped, because the end was reached.");
        }
    }

    private void reschedule() {
        if (isRunning) {
            timer.cancel();
            timer.scheduleRepeating((int) (1000 * delay));
        }
    }

    @Override
    public void start() {
        log.info("Starting slideshow");
        isRunning = true;
        navigator.slideshow();
        reschedule();
        fireStatusChanged();
    }

    @Override
    public void stop() {
        log.info("Stopping slideshow");
        isRunning = false;
        timer.cancel();
        fireStatusChanged();
        navigator.unslideshow();
    }

    @Override
    public void pause() {
        log.info("Pause slideshow");
        isRunning = false;
        timer.cancel();
        fireStatusChanged();
    }

    @Override
    public float faster() {
        delay /= INCREASE_FACTOR;
        fireStatusChanged();
        reschedule();

        return delay();
    }

    @Override
    public float slower() {
        delay *= INCREASE_FACTOR;
        fireStatusChanged();
        reschedule();

        return delay();
    }

    private void fireStatusChanged() {
        eventBus.fireEvent(new SlideshowStatusEvent(isRunning, delay()));
    }

    @Override
    public float delay() {
        return delay;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }
}

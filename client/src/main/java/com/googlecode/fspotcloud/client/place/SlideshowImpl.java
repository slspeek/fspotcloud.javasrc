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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.fspotcloud.client.main.event.SlideshowStatusEvent;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.place.api.Navigator;
import com.googlecode.fspotcloud.client.place.api.Navigator.Direction;
import com.googlecode.fspotcloud.client.place.api.Navigator.Unit;
import com.googlecode.fspotcloud.client.place.api.Slideshow;
import com.googlecode.fspotcloud.keyboardaction.TimerInterface;

import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Level.FINER;


public class SlideshowImpl implements Slideshow {
    private final Logger log = Logger.getLogger(SlideshowImpl.class.getName());
    private final Navigator navigator;
    private final TimerInterface timer;
    private boolean isRunning = false;
    private final float INCREASE_FACTOR = 4f / 3f;
    private float delay = 4f;
    private final EventBus eventBus;
    private final IPlaceController placeController;

    @Inject
    public SlideshowImpl(Navigator navigator,
                         TimerInterface timer,
                         EventBus eventBus,
                         IPlaceController placeController) {
        this.eventBus = eventBus;
        this.navigator = navigator;
        this.timer = timer;
        this.placeController = placeController;
        initTimer();
        log.log(FINER, "Created");
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
            log.log(FINE, "Timer stopped, because the end was reached.");
        }
    }

    private void reschedule() {
        timer.cancel();
        timer.scheduleRepeating((int) (1000 * delay));
    }

    @Override
    public void start() {
        log.log(FINE, "Starting slideshow");
        isRunning = true;
        slideshow();
        reschedule();
        fireStatusChanged();
    }

    @Override
    public void stop() {
        log.log(FINE, "Stopping slideshow");
        isRunning = false;
        timer.cancel();
        fireStatusChanged();
        unslideshow();
    }

    @Override
    public void pause() {
        log.log(FINE, "Pause slideshow");
        isRunning = false;
        timer.cancel();
        fireStatusChanged();
    }

    @Override
    public void togglePause() {
        if (isRunning) {
            pause();
        } else {
            start();
        }
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


    void slideshow() {
        BasePlace now = placeController.where();
        if (now != null) {
            SlideshowPlace destination = new SlideshowPlace(now.getTagId(),
                    now.getPhotoId());
            placeController.goTo(destination);
        }
    }


    void unslideshow() {
        BasePlace fromPlace = placeController.where();
        BasePlace result;
        if (fromPlace instanceof SlideshowPlace) {
            PlaceBuilder builder = new PlaceBuilder(fromPlace);
            result = builder.place();
            placeController.goTo(result);
        }
    }

}

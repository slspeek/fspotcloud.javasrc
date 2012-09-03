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

package com.googlecode.fspotcloud.client.demo;

import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.fspotcloud.client.main.event.ActionFamily;
import com.googlecode.fspotcloud.client.main.event.ActionMap;
import com.googlecode.fspotcloud.client.main.event.application.ApplicationType;
import com.googlecode.fspotcloud.client.main.event.navigation.NavigationType;
import com.googlecode.fspotcloud.client.main.event.raster.RasterType;
import com.googlecode.fspotcloud.client.main.view.DemoPresenter;
import com.googlecode.fspotcloud.client.view.action.KeyDispatcher;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;
import com.googlecode.fspotcloud.client.view.action.api.UserActionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class DemoAction implements Runnable {
    private final Logger log = Logger.getLogger(KeyDispatcher.class.getName());
    List<DemoStep> demo;
    int stepPointer = -1;
    private final DemoStepFactory factory;
    private final ActionFamily actions;
    private final UserActionFactory shortcutFactory;
    private final Provider<DemoPresenter> demoPresenterProvider;

    @Inject
    public DemoAction(DemoStepFactory factory,
                      UserActionFactory shortcutFactory, ActionFamily actions,
                      Provider<DemoPresenter> demoPresenterProvider) {
        this.factory = factory;
        this.actions = actions;
        this.shortcutFactory = shortcutFactory;
        this.demoPresenterProvider = demoPresenterProvider;
    }

    private void initDemo() {
        demo = new ArrayList<DemoStep>();

        ActionMap raster = actions.get("Raster");
        addStep(raster.get(RasterType.SET_RASTER_2x2), 3000);
        addStep(raster.get(RasterType.SET_RASTER_3x3), 2000);
        addStep(raster.get(RasterType.SET_RASTER_4x4), 4000);
        addStep(raster.get(RasterType.ADD_COLUMN), 1000);
        addStep(raster.get(RasterType.ADD_COLUMN), 4000,
                "You can do this again and again");
        addStep(raster.get(RasterType.TOGGLE_TABULAR_VIEW), 2000);

        ActionMap navigation = actions.get("Navigation");
        addStep(navigation.get(NavigationType.END), 2500);
        addStep(navigation.get(NavigationType.BACK), 2500);
        addStep(navigation.get(NavigationType.HOME), 2500);
        addStep(navigation.get(NavigationType.NEXT), 2500);
        addStep(raster.get(RasterType.TOGGLE_TABULAR_VIEW), 3000);

        ActionMap application = actions.get("Application");
        addStep(raster.get(RasterType.SET_DEFAULT_RASTER), 4000);
        addStep(application.get(ApplicationType.TOGGLE_HELP), 5000);
        addStep(application.get(ApplicationType.TOGGLE_HELP), 1000,
                "Again to hide the help.");
    }

    private void addStep(UserAction shortcut, int pause) {
        DemoStep step = factory.getDemoStep(shortcut, pause);
        demo.add(step);
    }

    private void addStep(UserAction shortcut, int pause,
                         String descriptionOverride) {
        DemoStep step = factory.getDemoStep(shortcutFactory.get(
                shortcut.getId(),
                shortcut.getCaption(),
                descriptionOverride,
                shortcut.getKey(),
                shortcut.getAlternateKey(),
                shortcut.getIcon(),
                shortcut.getEventProvider()), pause);
        demo.add(step);
    }

    @Override
    public void run() {
        initDemo();
        stepPointer++;

        if (stepPointer < demo.size()) {
            DemoStep step = demo.get(stepPointer);
            DemoRunner runner = new DemoRunner(step, this);
            final DemoPresenter demoPresenter = demoPresenterProvider.get();
            demoPresenter.setText(step.getDescription());
            demoPresenter.show();

            Timer hideTimer = new Timer() {
                @Override
                public void run() {
                    demoPresenter.hide();
                }
            };

            hideTimer.schedule(step.pauseTime());
        } else {
            log.info("Demo ended");

            final DemoPresenter demoPresenter = demoPresenterProvider.get();
            demoPresenter.setText("Demo ended.<br> Thank you.");
            demoPresenter.show();

            Timer hideTimer = new Timer() {
                @Override
                public void run() {
                    demoPresenter.hide();
                }
            };

            hideTimer.schedule(3000);
            stepPointer = -1;
        }
    }
}

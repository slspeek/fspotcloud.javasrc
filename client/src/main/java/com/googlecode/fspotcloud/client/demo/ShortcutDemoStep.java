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

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.fspotcloud.client.main.help.HelpContentGenerator;
import com.googlecode.fspotcloud.client.view.action.api.ShortcutHandler;
import com.googlecode.fspotcloud.client.view.action.api.UserAction;


public class ShortcutDemoStep implements DemoStep {
    private final ShortcutHandler shortcutHandler;
    private final UserAction shortcut;
    private final int pause;
    private final HelpContentGenerator generator;

    @Inject
    public ShortcutDemoStep(HelpContentGenerator generator,
                            ShortcutHandler shortcutHandler, @Assisted
    UserAction shortcut, @Assisted
    int pause) {
        this.shortcutHandler = shortcutHandler;
        this.generator = generator;
        this.shortcut = shortcut;
        this.pause = pause;
    }

    @Override
    public Runnable getAction() {
        return new Runnable() {
            @Override
            public void run() {
                shortcutHandler.handle(shortcut.getKey().getKeyCode());
            }
        };
    }

    @Override
    public int pauseTime() {
        return pause;
    }

    @Override
    public String getDescription() {
        return generator.getHelpText(shortcut);
    }
}

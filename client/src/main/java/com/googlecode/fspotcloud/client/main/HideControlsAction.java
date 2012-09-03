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

package com.googlecode.fspotcloud.client.main;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.view.action.api.IGlobalShortcutController;
import com.googlecode.fspotcloud.client.view.action.api.IGlobalShortcutController.Mode;
import com.googlecode.fspotcloud.client.main.ui.TagViewImpl;
import com.googlecode.fspotcloud.client.main.view.api.TagView;

import java.util.logging.Logger;


public class HideControlsAction implements Runnable {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(HideControlsAction.class.getName());
    private final TagViewImpl tagView;
    private final IGlobalShortcutController keyboard;

    @Inject
    public HideControlsAction(TagView tagView,
                              IGlobalShortcutController keyboard) {
        super();
        this.tagView = (TagViewImpl) tagView;
        this.keyboard = keyboard;
    }

    @Override
    public void run() {
        tagView.animateControlsOut(0);
        keyboard.setMode(Mode.TAG_VIEW);
    }
}

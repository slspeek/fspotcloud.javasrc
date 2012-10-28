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

package com.googlecode.fspotcloud.client.useraction.application.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.TagViewImpl;
import com.googlecode.fspotcloud.client.main.view.api.TagView;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.keyboardaction.IModeController;

import java.util.logging.Logger;


public class HideControlsHandler implements IActionHandler {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(HideControlsHandler.class.getName());
    private final TagViewImpl tagView;
    private final IModeController modeController;

    @Inject
    public HideControlsHandler(TagView tagView,
                               IModeController modeController) {
        super();
        this.tagView = (TagViewImpl) tagView;
        this.modeController = modeController;
    }

    @Override
    public void performAction(String actionId) {
        tagView.animateControlsOut(0);
        modeController.setMode(Modes.TAG_VIEW);
    }
}

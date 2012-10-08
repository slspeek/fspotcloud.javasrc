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
import com.googlecode.fspotcloud.client.main.view.AboutPresenter;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;


public class AboutAction implements IActionHandler {
    private final AboutPresenter aboutPresenter;
    private boolean isShowing = false;

    @Inject
    public AboutAction(AboutPresenter aboutPresenter) {
        this.aboutPresenter = aboutPresenter;
    }

    @Override
    public void performAction(String actionId) {
        if (isShowing) {
            aboutPresenter.hide();
            isShowing = false;
        } else {
            aboutPresenter.show();
            isShowing = true;
        }
    }
}

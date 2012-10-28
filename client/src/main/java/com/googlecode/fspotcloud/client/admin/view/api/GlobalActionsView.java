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

package com.googlecode.fspotcloud.client.admin.view.api;

import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;


public interface GlobalActionsView extends IsWidget {
    void setPresenter(GlobalActionsPresenter presenter);

    HasText getTagCountValue();

    HasText getPhotoCountOnPeerValue();

    HasText getPendingCommandCountValue();

    HasText getLastSeenPeerValue();

    HasEnabled getDeleteAllTagsButton();

    HasEnabled getDeleteAllCommandsButton();

    HasEnabled getUpdateButton();

    boolean confirm(String message);

    interface GlobalActionsPresenter {
        void init();

        void deleteAllCommands();

        void deleteAllTags();

        void update();
    }
}

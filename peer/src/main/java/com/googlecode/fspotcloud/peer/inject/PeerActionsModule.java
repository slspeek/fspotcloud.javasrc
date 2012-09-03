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

package com.googlecode.fspotcloud.peer.inject;

import com.googlecode.fspotcloud.peer.handlers.*;
import com.googlecode.fspotcloud.shared.peer.*;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;


public class PeerActionsModule extends ActionHandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetPeerMetaDataAction.class, GetPeerMetaDataHandler.class);
        bindHandler(GetTagDataAction.class, GetTagDataHandler.class);
        bindHandler(GetPhotoDataAction.class, GetPhotoDataHandler.class);
        bindHandler(GetTagUpdateInstructionsAction.class,
                GetTagUpdateInstructionsHandler.class);
        bindHandler(GetPeerUpdateInstructionsAction.class,
                GetPeerUpdateInstructionsHandler.class);
        bindHandler(GetFullsizePhotoAction.class, GetFullsizePhotoHandler.class);
    }
}

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

package com.googlecode.fspotcloud.server.inject;

import com.googlecode.fspotcloud.server.admin.handler.*;
import com.googlecode.fspotcloud.shared.dashboard.*;
import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;


public class AdminActionsModule extends ActionHandlerModule {
    @Override
    protected void configureHandlers() {
        bindHandler(GetMetaDataAction.class, GetMetaDataHandler.class);
        bindHandler(UserDeletesAllAction.class, UserDeletesAllHandler.class);
        bindHandler(UserDeletesAllCommandsAction.class,
                UserDeletesAllCommandsHandler.class);
        bindHandler(UserImportsTagAction.class, UserImportsTagHandler.class);
        bindHandler(UserUnImportsTagAction.class, UserUnImportsTagHandler.class);
        bindHandler(UserSynchronizesPeerAction.class,
                UserSynchronizesPeerHandler.class);
        bindHandler(GetAdminTagTreeAction.class, GetAdminTagTreeHandler.class);
    }
}

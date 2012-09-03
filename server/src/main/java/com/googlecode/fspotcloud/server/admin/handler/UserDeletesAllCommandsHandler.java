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

package com.googlecode.fspotcloud.server.admin.handler;

import com.google.inject.Inject;
import com.googlecode.botdispatch.model.api.Commands;
import com.googlecode.fspotcloud.shared.dashboard.UserDeletesAllCommandsAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Provider;
import java.util.logging.Logger;


public class UserDeletesAllCommandsHandler extends SimpleActionHandler<UserDeletesAllCommandsAction, VoidResult> {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(UserDeletesAllCommandsHandler.class.getName());
    private final Commands commandManager;
    private final Provider<UserService> userServiceProvider;

    @Inject
    public UserDeletesAllCommandsHandler(Commands commandManager,
                                         Provider<UserService> userServiceProvider) {
        super();
        this.commandManager = commandManager;
        this.userServiceProvider = userServiceProvider;
    }

    @Override
    public VoidResult execute(UserDeletesAllCommandsAction action,
                              ExecutionContext context) throws DispatchException {
        UserService userService = userServiceProvider.get();

        if (!userService.isUserAdmin()) {
            throw new SecurityException("Not admin");
        }

        try {
            commandManager.deleteAll();
        } catch (Exception e) {
            throw new ActionException(e);
        }

        return new VoidResult();
    }
}

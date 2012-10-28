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

package com.googlecode.fspotcloud.server.cron;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.shared.dashboard.UserSynchronizesPeerAction;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@SuppressWarnings("serial")
@Singleton
public class CronServlet extends HttpServlet {
    @Inject
    @VisibleForTesting
    Dispatch dispatch;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String action = request.getParameter("action");

            if (action.equals("synchronize-peer")) {
                dispatch.execute(new UserSynchronizesPeerAction());
            }
        } catch (DispatchException e) {
            response.getOutputStream().println(e.getMessage());
        }
    }
}

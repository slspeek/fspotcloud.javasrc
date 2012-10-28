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

package com.googlecode.fspotcloud.user.emailconfirmation;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.server.model.api.User;
import com.googlecode.fspotcloud.server.model.api.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;


@Singleton
public class ConfirmationServlet extends HttpServlet {
    @VisibleForTesting
    @Inject
    UserDao userDao;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String secret = request.getParameter("secret");
        String email = request.getParameter("email");
        User user = userDao.find(email);
        final String storedSecret = user.emailVerificationSecret();
        Logger.getAnonymousLogger()
                .info("EM: " + email + " secret: " + secret + " stored-s: " +
                        storedSecret);

        PrintWriter out = response.getWriter();

        if (secret.equals(storedSecret)) {
            user.setEnabled(true);
            userDao.save(user);
            out.println("Success");
            out.close();
        } else {
            out.println("Failure");
            out.close();
        }
    }
}

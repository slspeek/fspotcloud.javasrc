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

package com.googlecode.fspotcloud.user.openid;

import com.googlecode.fspotcloud.user.ISessionEmail;
import com.googlecode.fspotcloud.user.PostThirdPartyLoginServlet;
import com.googlecode.fspotcloud.user.PostThirdPartyLoginWorker;
import com.googlecode.fspotcloud.user.SessionEmail;
import com.googlecode.fspotcloud.user.inject.AbstractUserModule;
import com.googlecode.fspotcloud.user.inject.AbstractUserServletModule;


public class OpenIdUserModule extends AbstractUserModule {
    private final String adminEmail;

    public OpenIdUserModule(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    @Override
    protected void configure() {
        super.configure();
        bind(String.class).annotatedWith(AdminEmail.class).toInstance(adminEmail);
        bind(com.googlecode.fspotcloud.user.UserService.class)
                .to(com.googlecode.fspotcloud.user.openid.OpenIdUserService.class);
        bind(ISessionEmail.class).to(SessionEmail.class);
        bind(PostThirdPartyLoginWorker.class).to(PostOpenIdLoginWorker.class);
        install(new UserServletModule());
    }

    private class UserServletModule extends AbstractUserServletModule {
        @Override
        protected void configureServlets() {
            super.configureServlets();
            serve("/post-login").with(PostThirdPartyLoginServlet.class);
        }
    }
}

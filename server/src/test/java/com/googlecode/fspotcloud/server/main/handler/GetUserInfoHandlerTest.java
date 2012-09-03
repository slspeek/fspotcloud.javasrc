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

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.shared.DispatchException;
import org.jukito.JukitoRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class GetUserInfoHandlerTest {
    @Inject
    GetUserInfoHandler handler;

    @Test
    public void executeUserLoggedOn(UserService service)
            throws DispatchException {
        when(service.isUserLoggedIn()).thenReturn(Boolean.TRUE);
        when(service.isUserAdmin()).thenReturn(Boolean.FALSE);
        when(service.getThirdPartyLoginURL()).thenReturn("login_url");
        when(service.getThirdPartyLogoutURL()).thenReturn("logout_url");
        when(service.getEmail()).thenReturn("foo@bar.com");

        UserInfo info = handler.execute(new GetUserInfo(""), null);
        Assert.assertEquals("foo@bar.com", info.getEmail());
        Assert.assertTrue(info.isLoggedIn());
    }

    @Test
    public void executeNoUserLoggedOn(UserService service)
            throws DispatchException {
        when(service.isUserLoggedIn()).thenReturn(Boolean.FALSE);
        when(service.isUserAdmin()).thenReturn(Boolean.FALSE);
        when(service.getThirdPartyLoginURL()).thenReturn("login_url");
        when(service.getThirdPartyLogoutURL()).thenReturn("logout_url");
        when(service.getEmail()).thenReturn(null);

        UserInfo info = handler.execute(new GetUserInfo(""), null);
        Assert.assertFalse(info.isLoggedIn());
    }
}

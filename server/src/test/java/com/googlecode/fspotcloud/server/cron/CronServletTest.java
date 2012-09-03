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

import com.googlecode.fspotcloud.shared.dashboard.UserSynchronizesPeerAction;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletTestCase;
import com.meterware.servletunit.ServletUnitClient;
import net.customware.gwt.dispatch.server.Dispatch;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CronServletTest extends ServletTestCase {
    ServletRunner sr;

    public CronServletTest(String name) {
        super(name);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        sr = new ServletRunner();
        sr.registerServlet("cron", CronServlet.class.getName());
    }

    @Test
    public void testThumb() throws Exception {
        Dispatch dispatch = mock(Dispatch.class);

        ServletUnitClient sc = sr.newClient();
        WebRequest request = new GetMethodWebRequest(
                "http://test.meterware.com/cron");
        request.setParameter("action", "synchronize-peer");

        InvocationContext ic = sc.newInvocation(request);
        CronServlet servlet = (CronServlet) ic.getServlet();
        servlet.dispatch = dispatch;
        servlet.service(ic.getRequest(), ic.getResponse());

        WebResponse response = ic.getServletResponse();
        assertNotNull("No response received", response);
        assertEquals("content type", "text/plain", response.getContentType());
        verify(dispatch).execute(new UserSynchronizesPeerAction());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        sr.shutDown();
    }
}

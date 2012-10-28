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

package com.googlecode.fspotcloud.user;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(JukitoRunner.class)
public class SessionEmailTest {
    public static final String RMS_FSF_ORG = "rms@example.com";
    public static final String EMAIL = "email";
    @Inject
    SessionEmail sessionEmail;
    @Inject
    HttpSession session;

    @Test
    public void testGetEmail() throws Exception {
        when(session.getAttribute(EMAIL)).thenReturn(newArrayList(RMS_FSF_ORG));

        String email = sessionEmail.getEmail();
        assertEquals(RMS_FSF_ORG, email);
    }
}

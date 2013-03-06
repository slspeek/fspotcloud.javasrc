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
package com.googlecode.fspotcloud.test;

public interface ILogin {
    String RMS_FSF_ORG = "rms@example.com";
    String RMS_CRED = "GNU";
    String SLS = "slspeek@gmail.com";
    String SLS_CRED = SLS;
    String SLS_EMAIL_VERIFICATION_SECRET = SLS;
    String NEEDS_CONFIRMATION = "needs@example.com";
    String NEEDS_CRED = "needs@example.com";
    String NEEDS_SECRET = "needs@example.com";

    void login() throws Exception;
}

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

package com.googlecode.fspotcloud.user.inject;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.user.*;
import com.googlecode.fspotcloud.user.emailconfirmation.RandomSecretGenerator;
import com.googlecode.fspotcloud.user.emailconfirmation.SecretGenerator;

public class AbstractUserModule extends AbstractModule {
	protected void configure() {
		bind(IAdminPermission.class).to(AdminPermission.class);
		bind(ILoginMetaData.class).to(LoginMetaData.class);
		bind(ILoginMetaDataUpdater.class).to(LoginMetaDataUpdater.class);
		bind(String.class).annotatedWith(ServerAddress.class).toProvider(
				ServerAddressProvider.class);
		bind(SecretGenerator.class).to(RandomSecretGenerator.class).in(
				Singleton.class);
	}
}

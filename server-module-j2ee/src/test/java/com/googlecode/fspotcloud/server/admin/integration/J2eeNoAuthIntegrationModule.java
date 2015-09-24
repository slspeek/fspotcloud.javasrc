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

package com.googlecode.fspotcloud.server.admin.integration;

import static org.mockito.Mockito.mock;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;
import com.googlecode.botdispatch.model.api.Commands;
import com.googlecode.botdispatch.model.command.CommandManager;
import com.googlecode.fspotcloud.model.jpa.J2eeModelModule;
import com.googlecode.simpleblobstore.BlobService;
import com.googlecode.simpleblobstore.j2ee.J2eeSimpleBlobstoreModule;


public class J2eeNoAuthIntegrationModule
        extends NoAuthPlaceHolderIntegrationModule {
	
    public J2eeNoAuthIntegrationModule(boolean shotwell) {
		super(shotwell);
	}

	@Override
    public void configure() {
        super.configure();
        install(Modules.override(new J2eeModelModule(3, "derby"), new J2eeSimpleBlobstoreModule()).with(new AbstractModule(){

			@Override
			protected void configure() {
				bind(BlobService.class).toInstance(mock(BlobService.class));
				
			}}));
        bind(Commands.class).to(CommandManager.class).in(Singleton.class);
    }
}

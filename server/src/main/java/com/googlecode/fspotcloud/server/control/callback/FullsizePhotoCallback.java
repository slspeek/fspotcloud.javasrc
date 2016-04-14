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

package com.googlecode.fspotcloud.server.control.callback;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.botdispatch.SerializableAsyncCallback;
import com.googlecode.fspotcloud.server.image.ImageHelper;
import com.googlecode.fspotcloud.server.mail.IMail;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.fspotcloud.shared.peer.FullsizePhotoResult;

public class FullsizePhotoCallback
		implements
			SerializableAsyncCallback<FullsizePhotoResult> {
	private static final long serialVersionUID = 246810426240427570L;
	@Inject
	private transient PhotoDao photoManager;
	@Inject
	private transient ImageHelper imageHelper;
	@Inject
	private transient Provider<IMail> mailerProvider;
	private String caller;

	public FullsizePhotoCallback(String caller) {
		this.caller = caller;
	}

	@Override
	public void onFailure(Throwable caught) {
	}

	@Override
	public void onSuccess(final FullsizePhotoResult fullsizePhotoResult) {
		final String imageId = fullsizePhotoResult.getPhotoId();
		final String key = fullsizePhotoResult.getFsBlobKey();
		Photo photo = photoManager.find(imageId);
		photo.setFullsizeImageBlobKey(key);
		photoManager.save(photo);
		byte[] fsImageData = imageHelper.getImage(photo,
				ImageHelper.Type.FULLSIZE);
		mailerProvider.get().send(
				caller,
				"Your requested image: " + imageId,
				"Dear " + caller + ",\nYour requested image: " + imageId
						+ " is in the attachment", fsImageData);
	}
}

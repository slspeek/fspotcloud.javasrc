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

package com.googlecode.fspotcloud.peer.handlers;

import java.util.List;
import java.util.Map;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.peer.db.Backend;
import com.googlecode.fspotcloud.shared.peer.FullsizePhotoResult;
import com.googlecode.fspotcloud.shared.peer.GetFullsizePhotoAction;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.client.BlobstoreClient;

public class GetFullsizePhotoHandler
		extends
			SimpleActionHandler<GetFullsizePhotoAction, FullsizePhotoResult> {
	private final Backend data;
	private final BlobstoreClient blobClient;

	@Inject
	public GetFullsizePhotoHandler(Backend data, BlobstoreClient blobClient) {
		super();
		this.data = data;
		this.blobClient = blobClient;
	}

	@Override
	public FullsizePhotoResult execute(GetFullsizePhotoAction action,
			ExecutionContext context) throws DispatchException {
		FullsizePhotoResult result;

		try {
			byte[] fsImage = data.getFullsizePhotoData(action.getImageKey());
			Map<String, byte[]> upload = Maps.newHashMap();
			upload.put("fullsize", fsImage);
			Map<String, List<BlobKey>> uploadedKeys = blobClient.upload(upload);
			BlobKey key = uploadedKeys.get("fullsize").get(0);
			result = new FullsizePhotoResult(action.getImageKey(),
					key.getKeyString());
		} catch (Exception e) {
			throw new ActionException(e);
		}

		return result;
	}
}

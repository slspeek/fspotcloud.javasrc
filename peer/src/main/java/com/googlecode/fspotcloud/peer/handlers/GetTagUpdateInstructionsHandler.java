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

import com.googlecode.fspotcloud.peer.db.Data;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.peer.GetTagUpdateInstructionsAction;
import com.googlecode.fspotcloud.shared.peer.PhotoRemovedFromTag;
import com.googlecode.fspotcloud.shared.peer.PhotoUpdate;
import com.googlecode.fspotcloud.shared.peer.TagUpdateInstructionsResult;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GetTagUpdateInstructionsHandler extends SimpleActionHandler<GetTagUpdateInstructionsAction, TagUpdateInstructionsResult> {
    private final Data data;

    @Inject
    public GetTagUpdateInstructionsHandler(Data data) {
        super();
        this.data = data;
    }

    @Override
    public TagUpdateInstructionsResult execute(
            GetTagUpdateInstructionsAction action,
            ExecutionContext context) throws DispatchException {
        TagUpdateInstructionsResult result = new TagUpdateInstructionsResult(new ArrayList<PhotoUpdate>(),
                new ArrayList<PhotoRemovedFromTag>());

        try {
            List<String> keysOnThePeer = data.getPhotoKeysInTag(action.getTagId());

            for (PhotoInfo photoInfo : action.getPhotosOnServer()) {
                checkForUpdates(action.getTagId(), photoInfo, result);
                keysOnThePeer.remove(photoInfo.getId());
            }

            for (String key : keysOnThePeer) {
                PhotoUpdate update = new PhotoUpdate(key);
                result.getToBoUpdated().add(update);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result.getToBoUpdated().size() > 1000) {
            List<PhotoUpdate> list = new ArrayList<PhotoUpdate>();
            list.addAll(result.getToBoUpdated().subList(0, 1000));
            result.getToBoUpdated().clear();
            result.getToBoUpdated().addAll(list);
        }

        return result;
    }

    private void checkForUpdates(String tagId, PhotoInfo photoInfo,
                                 TagUpdateInstructionsResult result) throws SQLException {
        String id = photoInfo.getId();

        if (data.isPhotoInTag(tagId, id)) {
            if (photoInfo.getVersion() != data.getPhotoDefaultVersion(id)) {
                result.getToBoUpdated().add(new PhotoUpdate(id));
            }
        } else {
            result.getToBoRemovedFromTag().add(new PhotoRemovedFromTag(id));
        }
    }
}

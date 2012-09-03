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

package com.googlecode.fspotcloud.server.control.task.handler.intern;

import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.control.callback.PhotoDataCallback;
import com.googlecode.fspotcloud.server.control.task.actions.intern.AbstractBatchAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.PhotoUpdateAction;
import com.googlecode.fspotcloud.shared.peer.GetPhotoDataAction;
import com.googlecode.fspotcloud.shared.peer.ImageSpecs;
import com.googlecode.fspotcloud.shared.peer.PhotoUpdate;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PhotoUpdateHandler extends AbstractBatchActionHandler<PhotoUpdateAction, PhotoUpdate> {
    private final int MAX_PHOTO_TICKS;
    private final ControllerDispatchAsync controllerDispatch;
    private final ImageSpecs imageSpecs;

    @Inject
    public PhotoUpdateHandler(@Named("maxTicks")
                              int maxTicks, @Named("maxPhotoTicks")
    int maxPhotoTicks, @Named("defaultImageSpecs")
    ImageSpecs imageSpecs, ControllerDispatchAsync controllerDispatch,
                              TaskQueueDispatch dispatchAsync) {
        super(dispatchAsync, maxTicks);
        this.controllerDispatch = controllerDispatch;
        MAX_PHOTO_TICKS = maxPhotoTicks;
        this.imageSpecs = imageSpecs;
    }

    @Override
    public void doWork(AbstractBatchAction<PhotoUpdate> action,
                       Iterator<PhotoUpdate> workLoad) {
        List<String> imageKeys = newArrayList();

        for (int j = 0; j < MAX_PHOTO_TICKS && workLoad.hasNext(); j++) {
            PhotoUpdate photoUpdate = workLoad.next();
            imageKeys.add(photoUpdate.getPhotoId());
        }

        GetPhotoDataAction botAction = new GetPhotoDataAction(imageSpecs,
                imageKeys);
        PhotoDataCallback callback = new PhotoDataCallback(null, null, null,
                null);
        controllerDispatch.execute(botAction, callback);
    }
}

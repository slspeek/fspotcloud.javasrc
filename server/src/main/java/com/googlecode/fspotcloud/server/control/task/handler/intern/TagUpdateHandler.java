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
import com.googlecode.fspotcloud.server.control.callback.TagDataCallback;
import com.googlecode.fspotcloud.server.control.task.actions.intern.AbstractBatchAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.TagUpdateAction;
import com.googlecode.fspotcloud.shared.peer.GetTagDataAction;
import com.googlecode.fspotcloud.shared.peer.TagUpdate;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TagUpdateHandler extends AbstractBatchActionHandler<TagUpdateAction, TagUpdate> {
    private final int MAX_DATA_TICKS;
    private final ControllerDispatchAsync controllerDispatch;

    @Inject
    public TagUpdateHandler(@Named("maxTicks")
                            int maxTicks, ControllerDispatchAsync controllerDispatch,
                            TaskQueueDispatch dispatchAsync) {
        super(dispatchAsync, maxTicks);
        this.controllerDispatch = controllerDispatch;
        MAX_DATA_TICKS = maxTicks;
    }

    @Override
    public void doWork(AbstractBatchAction<TagUpdate> action,
                       Iterator<TagUpdate> workLoad) {
        List<String> tagKeys = new ArrayList<String>();

        for (int j = 0; workLoad.hasNext() && j < MAX_DATA_TICKS; j++) {
            TagUpdate tagUpdate = workLoad.next();
            tagKeys.add(tagUpdate.getTagId());
        }

        GetTagDataAction botAction = new GetTagDataAction(tagKeys);
        TagDataCallback callback = new TagDataCallback(null, null);
        controllerDispatch.execute(botAction, callback);
    }
}

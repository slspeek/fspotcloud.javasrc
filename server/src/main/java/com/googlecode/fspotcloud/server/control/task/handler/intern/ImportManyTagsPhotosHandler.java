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

import com.googlecode.fspotcloud.server.control.task.actions.intern.AbstractBatchAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.ImportManyTagsPhotosAction;
import com.googlecode.fspotcloud.shared.dashboard.UserImportsTagAction;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;
import java.util.logging.Logger;


public class ImportManyTagsPhotosHandler extends AbstractBatchActionHandler<ImportManyTagsPhotosAction, String> {
    private final Logger log = Logger.getLogger(ImportManyTagsPhotosHandler.class.getName());
    private final TaskQueueDispatch dispatchAsync;

    @Inject
    public ImportManyTagsPhotosHandler(TaskQueueDispatch dispatchAsync,
                                       @Named("maxTicks")
                                       int MAX_DATA_TICKS) {
        super(dispatchAsync, MAX_DATA_TICKS);
        this.dispatchAsync = dispatchAsync;
    }

    @Override
    public void doWork(AbstractBatchAction<String> action,
                       Iterator<String> workLoad) {
        dispatchAsync.execute(new UserImportsTagAction(workLoad.next()));
    }
}

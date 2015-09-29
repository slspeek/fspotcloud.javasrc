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

import java.util.logging.Logger;

import javax.inject.Inject;

import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllTagsAction;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;

public class DeleteTagsHandler
		extends
			SimpleActionHandler<DeleteAllTagsAction, VoidResult> {
	private final Logger log = Logger.getLogger(DeleteTagsHandler.class
			.getName());
	private final TaskQueueDispatch dispatchAsync;
	private final TagDao tagManager;
	private final PeerDatabaseDao peerDatabaseDao;

	@Inject
	public DeleteTagsHandler(TaskQueueDispatch dispatchAsync,
			TagDao tagManager, PeerDatabaseDao peerDatabaseDao) {
		super();
		this.dispatchAsync = dispatchAsync;
		this.tagManager = tagManager;
		this.peerDatabaseDao = peerDatabaseDao;
	}

	@Override
	public VoidResult execute(DeleteAllTagsAction action,
			ExecutionContext context) throws DispatchException {
		log.info("Delete tags entered");
		tagManager.deleteBulk(30);

		if (!tagManager.isEmpty()) {
			dispatchAsync.execute(new DeleteAllTagsAction());
		} else {
			peerDatabaseDao.resetCachedTagTrees();
		}

		return new VoidResult();
	}
}

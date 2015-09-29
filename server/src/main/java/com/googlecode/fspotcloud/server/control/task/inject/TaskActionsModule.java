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

package com.googlecode.fspotcloud.server.control.task.inject;

import net.customware.gwt.dispatch.server.guice.ActionHandlerModule;

import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllPhotosAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.DeleteAllTagsAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.ImportManyTagsPhotosAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.PhotoUpdateAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemovePhotosFromTagAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RemoveTagsDeletedFromPeerAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.RssFeedAction;
import com.googlecode.fspotcloud.server.control.task.actions.intern.TagUpdateAction;
import com.googlecode.fspotcloud.server.control.task.handler.intern.DeleteAllPhotosHandler;
import com.googlecode.fspotcloud.server.control.task.handler.intern.DeleteTagsHandler;
import com.googlecode.fspotcloud.server.control.task.handler.intern.ImportManyTagsPhotosHandler;
import com.googlecode.fspotcloud.server.control.task.handler.intern.PhotoUpdateHandler;
import com.googlecode.fspotcloud.server.control.task.handler.intern.RemovePhotosFromTagHandler;
import com.googlecode.fspotcloud.server.control.task.handler.intern.RemoveTagsFromPeerHandler;
import com.googlecode.fspotcloud.server.control.task.handler.intern.TagUpdateHandler;
import com.googlecode.fspotcloud.server.main.handler.RssFeedHandler;

public class TaskActionsModule extends ActionHandlerModule {
	@Override
	protected void configureHandlers() {
		bindHandler(PhotoUpdateAction.class, PhotoUpdateHandler.class);
		bindHandler(DeleteAllTagsAction.class, DeleteTagsHandler.class);
		bindHandler(DeleteAllPhotosAction.class, DeleteAllPhotosHandler.class);
		bindHandler(RemovePhotosFromTagAction.class,
				RemovePhotosFromTagHandler.class);
		bindHandler(RemoveTagsDeletedFromPeerAction.class,
				RemoveTagsFromPeerHandler.class);
		bindHandler(TagUpdateAction.class, TagUpdateHandler.class);
		bindHandler(ImportManyTagsPhotosAction.class,
				ImportManyTagsPhotosHandler.class);
		bindHandler(RssFeedAction.class, RssFeedHandler.class);
	}
}

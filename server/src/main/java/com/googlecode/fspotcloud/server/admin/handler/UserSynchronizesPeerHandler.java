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

package com.googlecode.fspotcloud.server.admin.handler;

import com.google.inject.Inject;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.server.control.callback.PeerMetaDataCallback;
import com.googlecode.fspotcloud.server.control.task.actions.intern.ImportManyTagsPhotosAction;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.dashboard.UserSynchronizesPeerAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.peer.GetPeerMetaDataAction;
import com.googlecode.fspotcloud.user.IAdminPermission;
import com.googlecode.taskqueuedispatch.TaskQueueDispatch;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.ActionException;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.util.List;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;

public class UserSynchronizesPeerHandler extends SimpleActionHandler<UserSynchronizesPeerAction, VoidResult> {
    @Inject
    private Logger log;
    private final ControllerDispatchAsync dispatch;
    private final IAdminPermission adminPermission;
    private final TaskQueueDispatch taskQueueDispatch;
    private final TagDao tagManager;

    @Inject
    public UserSynchronizesPeerHandler(ControllerDispatchAsync dispatch,
                                       IAdminPermission adminPermission, TaskQueueDispatch taskQueueDispatch,
                                       TagDao tagManager) {
        super();
        this.dispatch = dispatch;
        this.adminPermission = adminPermission;
        this.taskQueueDispatch = taskQueueDispatch;
        this.tagManager = tagManager;
    }

    @Override
    public VoidResult execute(UserSynchronizesPeerAction action,
                              ExecutionContext context) throws DispatchException {
        adminPermission.checkAdminPermission();

        try {
            GetPeerMetaDataAction metaAction = new GetPeerMetaDataAction();
            PeerMetaDataCallback callback = new PeerMetaDataCallback();
            dispatch.execute(metaAction, callback);

            final List<String> importTagIds = getImportTagIds();
            log.info("before import many tags photos " + importTagIds);
            taskQueueDispatch.execute(new ImportManyTagsPhotosAction(
                    importTagIds));
        } catch (Exception e) {
            throw new ActionException(e);
        }

        return new VoidResult();
    }

    private List<String> getImportTagIds() {
        List<Tag> importedTags = tagManager.getImportedTags();
        List<String> idList = newArrayList();

        for (Tag tag : importedTags) {
            idList.add(tag.getId());
        }

        return idList;
    }
}

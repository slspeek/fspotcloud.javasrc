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

package com.googlecode.fspotcloud.server.main.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.server.model.api.UserGroup;
import com.googlecode.fspotcloud.server.model.api.UserGroupDao;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.RevokeTagAction;
import com.googlecode.fspotcloud.user.IAdminPermission;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.util.Set;


public class RevokeTagHandler extends SimpleActionHandler<RevokeTagAction, VoidResult> {
    private final UserGroupDao userGroupDao;
    private final TagDao tagDao;
    private final IAdminPermission adminPermission;

    @Inject
    public RevokeTagHandler(UserGroupDao userGroupDao, TagDao tagDao,
                            IAdminPermission adminPermission) {
        this.userGroupDao = userGroupDao;
        this.tagDao = tagDao;
        this.adminPermission = adminPermission;
    }

    @Override
    public VoidResult execute(RevokeTagAction action, ExecutionContext context)
            throws DispatchException {
        adminPermission.checkAdminPermission();

        Tag tag = tagDao.findOrNew(action.getTagId());
        Set<Long> approvedGroups = tag.getApprovedUserGroups();
        approvedGroups.remove(action.getUserGroupId());
        tag.setApprovedUserGroups(approvedGroups);
        tagDao.save(tag);

        UserGroup userGroup = userGroupDao.find(action.getUserGroupId());

        if (userGroup != null) {
            Set<String> approvedTags = userGroup.getApprovedTagIds();
            approvedTags.remove(action.getTagId());
            userGroup.setApprovedTagIds(approvedTags);
            userGroupDao.save(userGroup);
        }

        return new VoidResult();
    }
}

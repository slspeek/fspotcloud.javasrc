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
import com.googlecode.fspotcloud.shared.main.*;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import java.util.SortedSet;


public class GetTagNodeHandler extends SimpleActionHandler<GetTagNodeAction, TagNodeResult> {
    private final TagDao tagDao;

    @Inject
    public GetTagNodeHandler(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public TagNodeResult execute(GetTagNodeAction action,
                                 ExecutionContext context) throws DispatchException {
        Tag tag = tagDao.find(action.getTagId());
        TagNode node = new TagNode();
        node.setId(tag.getId());
        node.setImportIssued(tag.isImportIssued());
        node.setParentId(tag.getParentId());
        node.setTagName(tag.getTagName());
        node.setCount(tag.getCount());

        SortedSet<PhotoInfo> photoList = tag.getCachedPhotoList();

        if (photoList != null) {
            node.setCachedPhotoList(new PhotoInfoStore(photoList));
        } else {
            throw new IllegalStateException(
                    "photoList field of Tag should not be null");
        }

        node.setApprovedUserGroups(tag.getApprovedUserGroups());

        return new TagNodeResult(node); //To change body of implemented methods use File | Settings | File Templates.
    }
}

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

package com.googlecode.fspotcloud.model.jpa.tag;

import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.fspotcloud.shared.main.PhotoInfoStore;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.simplejpadao.SimpleDAONamedIdImpl;

import java.util.List;
import java.util.SortedSet;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;

public abstract class TagManagerBase<T extends Tag, U extends T>
        extends SimpleDAONamedIdImpl<Tag, U, String> implements TagDao {
    private final Logger log = Logger.getLogger(TagManagerBase.class.getName());

    public static TagNode getTagNode(Tag tag) {
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

        return node;
    }

    @Override
    public List<TagNode> getTags() {
        List<TagNode> result = newArrayList();

        for (Tag tag : findAll(1000)) {
            TagNode node = getTagNode(tag);
            result.add(node);
        }

        return result;
    }

    @Override
    public List<Tag> getImportedTags() {
        return findAllWhere(1000, "importIssued = true");
    }

    protected abstract Tag newTag();
}

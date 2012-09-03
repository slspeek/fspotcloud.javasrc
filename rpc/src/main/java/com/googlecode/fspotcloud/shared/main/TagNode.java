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

package com.googlecode.fspotcloud.shared.main;

import com.google.common.annotations.GwtCompatible;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

@GwtCompatible
public class TagNode implements Serializable {
    private int count;
    private String description;
    private boolean importIssued;
    private String id;
    private TagNode parent;
    private String parentId;
    private String tagName;
    private PhotoInfoStore cachedPhotoList = new PhotoInfoStore(Collections.EMPTY_LIST);
    private List<TagNode> children = newArrayList();
    private Set<Long> approvedUserGroups = newHashSet();

    public TagNode() {
    }

    public TagNode(TagNode node) {
        this.id = node.getId();
        this.count = node.getCount();
        this.description = node.getDescription();
        this.importIssued = node.isImportIssued();
        this.tagName = node.getTagName();
        this.cachedPhotoList = node.getCachedPhotoList();
        this.approvedUserGroups = node.getApprovedUserGroups();
    }

    public TagNode(String id) {
        this(id, null);
    }

    public TagNode(String id, String parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public Set<Long> getApprovedUserGroups() {
        return approvedUserGroups;
    }

    public void setApprovedUserGroups(Set<Long> approvedUserGroups) {
        this.approvedUserGroups = approvedUserGroups;
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }

    public String getDescription() {
        return description;
    }

    public TagNode getParent() {
        return parent;
    }

    public String getParentId() {
        return parentId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setParent(TagNode parent) {
        this.parent = parent;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public void setChildren(List<TagNode> children) {
        this.children = children;
    }

    public List<TagNode> getChildren() {
        return children;
    }

    public void addChild(TagNode child) {
        getChildren().add(child);
    }

    public boolean equals(Object other) {
        if (!(other instanceof TagNode)) {
            return false;
        }

        TagNode node = (TagNode) other;

        return equal(node.getId(), getId());
    }

    public int hashCode() {
        return getId().hashCode();
    }

    public String toString() {
        return "TagNode(" + String.valueOf(tagName) + ": " +
                String.valueOf(id) + " " + String.valueOf(cachedPhotoList) + ")";
    }

    public void setCachedPhotoList(PhotoInfoStore cachedPhotoList) {
        this.cachedPhotoList = cachedPhotoList;
    }

    public PhotoInfoStore getCachedPhotoList() {
        return cachedPhotoList;
    }

    public void setImportIssued(boolean importIssued) {
        this.importIssued = importIssued;
    }

    public boolean isImportIssued() {
        return importIssued;
    }
}

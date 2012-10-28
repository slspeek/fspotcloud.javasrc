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

/**
 *
 */
package com.googlecode.fspotcloud.model.jpa.gae.tag;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.fspotcloud.server.model.api.Tag;
import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import org.apache.commons.lang.SerializationUtils;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.collect.Sets.newHashSet;

/**
 * Represents a Label in F-Spot
 */
@Entity
public class TagEntity implements Serializable, Tag {
    @Id
    private String id;
    @Basic
    private String tagName;
    @Basic
    private String parentId;
    @Basic
    private String parent;
    @Basic
    private int count;
    @Basic
    private boolean importIssued = false;
    @Basic
    private HashSet<Long> approvedUserGroups = newHashSet();
    @Basic
    private Blob cachedPhotoList = new Blob(SerializationUtils.serialize(
            new TreeSet<PhotoInfo>()));

    public TagEntity() {
    }

    public HashSet<Long> getApprovedUserGroups() {
        return approvedUserGroups;
    }

    public void setApprovedUserGroups(Set<Long> approvedUserGroups) {
        if (approvedUserGroups != null) {
            this.approvedUserGroups = newHashSet(approvedUserGroups);
        } else {
            this.approvedUserGroups = newHashSet();
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getParent() {
        return parent;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String getTagName() {
        return tagName;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public void setImportIssued(boolean importIssued) {
        this.importIssued = importIssued;
    }

    @Override
    public boolean isImportIssued() {
        return importIssued;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setCachedPhotoList(TreeSet<PhotoInfo> cachedPhotoList) {
        this.cachedPhotoList = new Blob(SerializationUtils.serialize(
                cachedPhotoList));
    }

    @Override
    public TreeSet<PhotoInfo> getCachedPhotoList() {
        return (TreeSet<PhotoInfo>) SerializationUtils.deserialize(cachedPhotoList.getBytes());
    }
}

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

package com.googlecode.fspotcloud.server.model.api;

import com.googlecode.fspotcloud.shared.main.PhotoInfo;
import com.googlecode.simplejpadao.HasSetKey;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;


public interface Tag extends HasSetKey<String>, Serializable {
    void setParent(String parent);

    String getParent();

    void setCount(int count);

    int getCount();

    void setTagName(String tagName);

    String getTagName();

    void setParentId(String parentId);

    String getParentId();

    void setCachedPhotoList(TreeSet<PhotoInfo> cachedPhotoList);

    TreeSet<PhotoInfo> getCachedPhotoList();

    void setImportIssued(boolean importIssued);

    boolean isImportIssued();

    void setApprovedUserGroups(Set<Long> keySet);

    Set<Long> getApprovedUserGroups();
}

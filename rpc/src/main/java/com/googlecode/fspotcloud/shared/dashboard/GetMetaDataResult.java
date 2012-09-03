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

package com.googlecode.fspotcloud.shared.dashboard;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.IsSerializable;
import net.customware.gwt.dispatch.shared.Result;

import java.util.Date;


@GwtCompatible
public class GetMetaDataResult implements IsSerializable, Result {
    private Date created;
    private int peerPhotoCount;
    private int tagCount;
    private int pendingCommandCount;
    private Date peerLastSeen;
    private String instanceName;
    private long photoCount;
    private Date photosLastCounted;

    public GetMetaDataResult() {
        created = new Date();
    }

    public long getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(long photoCount) {
        this.photoCount = photoCount;
    }

    public Date getPhotosLastCounted() {
        return photosLastCounted;
    }

    public void setPhotosLastCounted(Date photosLastCounted) {
        this.photosLastCounted = photosLastCounted;
    }

    public Date getCreated() {
        return created;
    }

    public int getPeerPhotoCount() {
        return peerPhotoCount;
    }

    public void setPeerPhotoCount(int peerPhotoCount) {
        this.peerPhotoCount = peerPhotoCount;
    }

    public int getTagCount() {
        return tagCount;
    }

    public void setTagCount(int tagCount) {
        this.tagCount = tagCount;
    }

    public Date getPeerLastSeen() {
        return peerLastSeen;
    }

    public void setPeerLastSeen(Date peerLastSeen) {
        this.peerLastSeen = peerLastSeen;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setPendingCommandCount(int pendingCommandCount) {
        this.pendingCommandCount = pendingCommandCount;
    }

    public int getPendingCommandCount() {
        return pendingCommandCount;
    }
}

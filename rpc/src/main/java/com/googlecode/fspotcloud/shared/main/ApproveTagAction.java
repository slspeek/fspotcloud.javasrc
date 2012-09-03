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
import com.google.common.base.Objects;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.shared.Action;


@GwtCompatible
public class ApproveTagAction implements Action<VoidResult> {
    private String tagId;
    private Long userGroupId;

    public ApproveTagAction(String tagId, Long userGroupId) {
        this.tagId = tagId;
        this.userGroupId = userGroupId;
    }

    public ApproveTagAction() {
    }

    public String getTagId() {
        return tagId;
    }

    public Long getUsergroupId() {
        return userGroupId;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("tagId", tagId)
                .add("usergroupId", userGroupId)
                .toString();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApproveTagAction that = (ApproveTagAction) o;

        if (tagId != null ? !tagId.equals(that.tagId) : that.tagId != null) return false;
        if (userGroupId != null ? !userGroupId.equals(that.userGroupId) : that.userGroupId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tagId != null ? tagId.hashCode() : 0;
        result = 31 * result + (userGroupId != null ? userGroupId.hashCode() : 0);
        return result;
    }
}

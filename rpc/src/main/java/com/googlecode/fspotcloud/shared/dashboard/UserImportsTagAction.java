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
import net.customware.gwt.dispatch.shared.Action;

import java.io.Serializable;


@GwtCompatible
public class UserImportsTagAction implements Action<VoidResult>,
        Serializable {
    private String tagId;

    public UserImportsTagAction(String tagId) {
        super();
        this.tagId = tagId;
    }

    /**
     * Needed for GWT-RPC
     */
    UserImportsTagAction() {
    }

    public String getTagId() {
        return tagId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final UserImportsTagAction other = (UserImportsTagAction) obj;

        if ((this.tagId == null) ? (other.tagId != null)
                : !this.tagId.equals(other.tagId)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.tagId != null ? this.tagId.hashCode() : 0);

        return hash;
    }
}

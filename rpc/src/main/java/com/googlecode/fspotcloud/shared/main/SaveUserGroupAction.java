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
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.shared.Action;


@GwtCompatible
public class SaveUserGroupAction implements Action<VoidResult> {
    UserGroupInfo infoToSave;

    public SaveUserGroupAction() {
    }

    public SaveUserGroupAction(UserGroupInfo infoToSave) {
        this.infoToSave = infoToSave;
    }

    public UserGroupInfo getInfoToSave() {
        return infoToSave;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("SaveUserGroupAction");
        sb.append("{infoToSave=").append(infoToSave);
        sb.append('}');

        return sb.toString();
    }
}

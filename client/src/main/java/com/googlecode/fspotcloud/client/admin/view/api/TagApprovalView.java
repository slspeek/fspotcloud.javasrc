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

package com.googlecode.fspotcloud.client.admin.view.api;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

import java.util.Set;


public interface TagApprovalView extends IsWidget {
    void setPresenter(TagApprovalPresenter presenter);

    void setApprovedGroups(Set<UserGroupInfo> approvedGroups);

    void setOtherGroups(Set<UserGroupInfo> approvedGroups);

    void setTagName(String name);

    UserGroupInfo getApprovedSelected();

    UserGroupInfo getOtherSelected();

    interface TagApprovalPresenter extends Activity {
        void remove();

        void approve();

        void setTagId(String tagId);

        void dashboard();
    }
}

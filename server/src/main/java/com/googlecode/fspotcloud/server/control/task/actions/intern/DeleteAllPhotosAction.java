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

package com.googlecode.fspotcloud.server.control.task.actions.intern;

import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import net.customware.gwt.dispatch.shared.Action;

import java.io.Serializable;


public class DeleteAllPhotosAction implements Action<VoidResult>,
        Serializable {
    private static final long serialVersionUID = 84293907017230375L;
    public static final int ANSWER_TO_EVERTHING = 42;

    public DeleteAllPhotosAction() {
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DeleteAllPhotosAction) {
            return true;
        } else {
            return false;
        }
    }


    public int hashCode() {
        return ANSWER_TO_EVERTHING; // any arbitrary constant will do
    }
}

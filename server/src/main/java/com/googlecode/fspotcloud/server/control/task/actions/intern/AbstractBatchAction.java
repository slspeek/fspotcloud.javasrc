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
import java.util.Iterator;
import java.util.List;


public abstract class AbstractBatchAction<T> implements Action<VoidResult>,
        Serializable {
    List<T> workLoad;

    public AbstractBatchAction(List<T> workLoad) {
        this.workLoad = workLoad;
    }

    public Iterator<T> iterator() {
        return new RemovingIterator<T>(workLoad.iterator());
    }

    public List<T> getWorkLoad() {
        return workLoad;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final AbstractBatchAction<T> other = (AbstractBatchAction<T>) obj;

        if (this.workLoad != other.workLoad &&
                (this.workLoad == null ||
                        !this.workLoad.equals(other.workLoad))) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash +
                (this.workLoad != null ? this.workLoad.hashCode() : 0);

        return hash;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("AbstractBatchAction");
        sb.append("{workLoad=").append(workLoad);
        sb.append('}');

        return sb.toString();
    }
}

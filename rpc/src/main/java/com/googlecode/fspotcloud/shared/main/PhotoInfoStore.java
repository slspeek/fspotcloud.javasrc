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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;


@GwtCompatible
public class PhotoInfoStore implements Serializable {
    private static final long serialVersionUID = 4509115035183737104L;
    private List<PhotoInfo> store;

    public PhotoInfoStore(List<PhotoInfo> data) {
        this.store = data;
    }

    public PhotoInfoStore(SortedSet<PhotoInfo> set) {
        this(new ArrayList<PhotoInfo>(set));
    }

    @SuppressWarnings("unused")
    private PhotoInfoStore() {
    }

    public PhotoInfo getInfo(String id) {
        for (PhotoInfo pi : store) {
            if (id.equals(pi.getId())) {
                return pi;
            }
        }

        return null;
    }

    public PhotoInfo get(int index) {
        return store.get(index);
    }

    public int size() {
        return store.size();
    }

    public int indexOf(String id) {
        int index = -1;
        ListIterator<PhotoInfo> it = store.listIterator();

        while (it.hasNext()) {
            PhotoInfo pi = it.next();

            if (id.equals(pi.getId())) {
                index = it.previousIndex();

                break;
            }
        }

        return index;
    }

    public boolean isEmpty() {
        return store.isEmpty();
    }

    public PhotoInfo last() {
        return store.get(lastIndex());
    }

    public int lastIndex() {
        return store.size() - 1;
    }

    public String toString() {
        return store.toString();
    }
}

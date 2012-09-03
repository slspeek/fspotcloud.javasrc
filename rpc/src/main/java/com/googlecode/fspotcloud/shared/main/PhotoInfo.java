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
import com.google.common.collect.ComparisonChain;

import java.io.Serializable;
import java.util.Date;


@GwtCompatible
public class PhotoInfo implements Serializable, Comparable<PhotoInfo> {
    private static final long serialVersionUID = -4084831085611916754L;
    private String id;
    private String description;
    private String exifData;
    private Date date;
    private int version;

    public PhotoInfo(String id, String description, Date date) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.version = 1;
    }

    public PhotoInfo(String id, String description, Date date, int version) {
        this(id, description, date);
        this.version = version;
    }

    public PhotoInfo(String id, String description, Date date, String exifData) {
        this(id, description, date);
        this.exifData = exifData;
    }

    @SuppressWarnings("unused")
    private PhotoInfo() {
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final PhotoInfo other = (PhotoInfo) obj;

        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }

        if ((this.description == null) ? (other.description != null)
                : !this.description.equals(
                other.description)) {
            return false;
        }

        if ((this.exifData == null) ? (other.exifData != null)
                : !this.exifData.equals(other.exifData)) {
            return false;
        }

        if (this.date != other.date &&
                (this.date == null || !this.date.equals(other.date))) {
            return false;
        }

        if (this.version != other.version) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 47 * hash +
                (this.description != null ? this.description.hashCode() : 0);
        hash = 47 * hash +
                (this.exifData != null ? this.exifData.hashCode() : 0);
        hash = 47 * hash + (this.date != null ? this.date.hashCode() : 0);
        hash = 47 * hash + this.version;

        return hash;
    }

    public int compareTo(PhotoInfo o) {
        return ComparisonChain.start().compare(date, o.date).compare(id, o.id)
                .result();
    }

    public String toString() {
        return "PhotoInfo(" + id + ")";
    }

    public void setExifData(String exifData) {
        this.exifData = exifData;
    }

    public String getExifData() {
        return exifData;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }
}

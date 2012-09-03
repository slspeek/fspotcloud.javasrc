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

/**
 *
 */
package com.googlecode.fspotcloud.model.jpa.gae.photo;

import com.googlecode.fspotcloud.server.model.api.Photo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.googlecode.fspotcloud.server.model.api.DateUtil.cloneDate;


/**
 * DOCUMENT ME!
 *
 * @author slspeek@gmail.com
 */
@Entity
public class PhotoEntity implements Photo, Serializable {
    @Id
    private String id;
    private String description;
    @Column(nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private ArrayList<String> tagList = new ArrayList<String>();
    private String fullsizeImageBlobKey;
    private String imageBlobKey;
    private String thumbBlobKey;

    public String getImageBlobKey() {
        return imageBlobKey;
    }

    public void setImageBlobKey(String imageBlobKey) {
        this.imageBlobKey = imageBlobKey;
    }

    public String getThumbBlobKey() {
        return thumbBlobKey;
    }

    public void setThumbBlobKey(String thumbBlobKey) {
        this.thumbBlobKey = thumbBlobKey;
    }

    public String getFullsizeImageBlobKey() {
        return fullsizeImageBlobKey;
    }

    public void setFullsizeImageBlobKey(String fullsizeImageBlobKey) {
        this.fullsizeImageBlobKey = fullsizeImageBlobKey;
    }

    @Override
    public void setId(String name) {
        this.id = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setTagList(List<String> tagList) {
        this.tagList = new ArrayList(tagList);
    }

    @Override
    public List<String> getTagList() {
        return tagList;
    }

    @Override
    public void setDate(Date date) {
        this.date = cloneDate(date);
    }

    @Override
    public Date getDate() {
        return cloneDate(date);
    }
}

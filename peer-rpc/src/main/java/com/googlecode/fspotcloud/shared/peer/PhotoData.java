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

package com.googlecode.fspotcloud.shared.peer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.openpojo.business.annotation.BusinessKey;

public class PhotoData extends BusinessBase implements Serializable {
	private static final long serialVersionUID = -4172714943981557358L;
	@BusinessKey
	private final String photoId;
	@BusinessKey
	private final String description;
	@BusinessKey
	private final Date date;

	@BusinessKey
	private final List<String> tagList;
	@BusinessKey
	private final int version;

	public PhotoData(String photoId, String description, Date date,
			List<String> tagList,
			int version) {
		super();
		this.photoId = photoId;
		if (description != null) {
			this.description = description.trim();
		} else {
			this.description = "";
		}
		this.date = date;
		this.tagList = tagList;
		this.version = version;
	}

	public int getVersion() {
		return version;
	}

	public String getPhotoId() {
		return photoId;
	}

	public String getDescription() {
		return description;
	}

	public Date getDate() {
		return date;
	}

	public List<String> getTagList() {
		return tagList;
	}

	public String toString() {
		return new ToStringBuilder(this).append("v", version)
				.append("id", photoId).append("desc", description).append("date", date)
				.append("tags", tagList)
				.toString();
	}
}

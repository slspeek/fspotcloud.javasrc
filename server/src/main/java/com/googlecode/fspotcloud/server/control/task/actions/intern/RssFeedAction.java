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

import net.customware.gwt.dispatch.shared.Action;
import net.customware.gwt.dispatch.shared.general.StringResult;

import java.io.Serializable;

public class RssFeedAction implements Action<StringResult>, Serializable {
	private static final long serialVersionUID = 8429390701736230375L;
	private final String tagId;

	public RssFeedAction(String tagId) {
		super();
		this.tagId = tagId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RssFeedAction) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return 0;
	}

	public String getTagId() {
		return tagId;
	}
}

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

package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.server.control.task.actions.intern.RssFeedAction;
import com.googlecode.fspotcloud.server.model.tag.FeedBuilder;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;
import net.customware.gwt.dispatch.shared.general.StringResult;

import javax.inject.Inject;
import java.util.logging.Logger;

public class RssFeedHandler
		extends
			SimpleActionHandler<RssFeedAction, StringResult> {
	private final Logger log = Logger.getLogger(RssFeedHandler.class.getName());

	private final Dispatch dispatch;
	private final FeedBuilder feedBuilder;

	@Inject
	public RssFeedHandler(Dispatch dispatch, FeedBuilder feedBuilder) {
		this.dispatch = dispatch;
		this.feedBuilder = feedBuilder;
	}

	@Override
	public StringResult execute(RssFeedAction action, ExecutionContext context)
			throws DispatchException {
		TagTreeResult tagTreeResult = dispatch.execute(new GetTagTreeAction());
		TagNode tagNode = TagNode.find(tagTreeResult.getTree(),
				action.getTagId());
		if (tagNode != null) {
			String rss = feedBuilder.getFeed(tagNode);
			return new StringResult(rss);
		} else {
			return new StringResult("Not found");
		}
	}

}

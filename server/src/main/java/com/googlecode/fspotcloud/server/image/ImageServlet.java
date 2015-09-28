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

package com.googlecode.fspotcloud.server.image;

import static com.google.common.collect.Sets.newHashSet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.server.model.api.Photo;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.fspotcloud.server.model.tag.IUserGroupHelper;
import com.googlecode.fspotcloud.user.UserService;
import com.googlecode.simpleblobstore.BlobKey;
import com.googlecode.simpleblobstore.BlobService;

/*
 * Courtesy to Felipe Gaucho
 */
@SuppressWarnings("serial")
@Singleton
public class ImageServlet extends HttpServlet {
	@VisibleForTesting
	@Inject
	PhotoDao photoManager;
	@Inject
	IUserGroupHelper userGroupHelper;
	@Inject
	UserService userService;
	@Inject
	BlobService blobService;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		boolean thumb = request.getParameter("thumb") != null;
		boolean fullSize = request.getParameter("fs") != null;
		Photo photo = photoManager.find(id);
		if (photo == null) {
			response.sendError(404);
			return;
		}

		if (userService.isUserAdmin()
				|| userGroupHelper
						.containsOneOf(newHashSet(photo.getTagList()))) {
			String blobKey;

			if (thumb) {
				blobKey = photo.getThumbBlobKey();
			} else if (fullSize) {
				blobKey = photo.getFullsizeImageBlobKey();
			} else {
				blobKey = photo.getImageBlobKey();
			}

			response.setContentType("image/jpeg");
			setCacheExpireDate(response, 3600 * 24 * 30);
			blobService.serve(new BlobKey(blobKey), response);
		}
	}

	public static void setCacheExpireDate(HttpServletResponse response,
			int seconds) {
		if (response != null) {
			Calendar cal = new GregorianCalendar();
			cal.roll(Calendar.SECOND, seconds);
			response.setHeader("Cache-Control", "PUBLIC, max-age=" + seconds
					+ ", must-revalidate");
			response.setHeader("Expires",
					htmlExpiresDateFormat().format(cal.getTime()));
		}
	}

	public static DateFormat htmlExpiresDateFormat() {
		DateFormat httpDateFormat = new SimpleDateFormat(
				"EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		return httpDateFormat;
	}
}

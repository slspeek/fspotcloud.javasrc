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

package com.googlecode.fspotcloud.client.main.view.api;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.fspotcloud.client.main.ui.ImageViewImpl;
import com.googlecode.fspotcloud.client.place.MailFullsizePlace;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

@GwtCompatible
public interface MailFullsizeView extends IsWidget, StatusView {
	ImageViewImpl getImageView();

	interface MailFullsizePresenter extends Activity, IActionHandler {
		MailFullsizePresenter withPlace(MailFullsizePlace place);
	}
}

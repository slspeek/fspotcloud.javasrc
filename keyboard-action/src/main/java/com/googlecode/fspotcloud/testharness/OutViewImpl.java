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

package com.googlecode.fspotcloud.testharness;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbar;

public class OutViewImpl extends Composite implements OutView {

	private static final OutViewImplUiBinder uiBinder = GWT
			.create(OutViewImplUiBinder.class);

	@UiField(provided = true)
	ActionToolbar toolbar;

	@Inject
	public OutViewImpl(ActionToolbar actionToolbar) {
		toolbar = actionToolbar;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setStatusText(String result) {
	}

	interface OutViewImplUiBinder extends UiBinder<Widget, OutViewImpl> {
	}
}

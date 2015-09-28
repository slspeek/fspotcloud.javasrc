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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.keyboardaction.gwt.ActionToolbar;

public class HomeViewImpl extends Composite implements HomeView {

	private static final HomeViewImplUiBinder uiBinder = GWT
			.create(HomeViewImplUiBinder.class);

	@UiField(provided = true)
	TextArea messageBox;
	@UiField(provided = true)
	ActionToolbar toolbar;

	@Inject
	public HomeViewImpl(ActionToolbar actionToolbar) {
		toolbar = actionToolbar;
		messageBox = MainFactory.messageBoard;
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setStatusText(String result) {
		messageBox.setText(result);
	}

	interface HomeViewImplUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}
}

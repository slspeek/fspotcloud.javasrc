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

package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ClientLoginManager;
import com.googlecode.fspotcloud.client.main.view.api.UserAccountView;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.UserInfo;

import java.util.logging.Logger;


public class UserAccountPresenterImpl extends AbstractActivity implements UserAccountView.UserAccountPresenter {
    private final Logger log = Logger.getLogger(UserAccountPresenterImpl.class.getName());
    private final UserAccountView view;
    private final ClientLoginManager clientLoginManager;

    @Inject
    public UserAccountPresenterImpl(UserAccountView view,
                                    ClientLoginManager clientLoginManager) {
        this.view = view;
        this.clientLoginManager = clientLoginManager;
    }

    @Override
    public void start(AcceptsOneWidget panel, EventBus eventBus) {
        this.view.setPresenter(this);
        panel.setWidget(view);
        clientLoginManager.getUserInfoAsync(new GetUserInfo(""),
                new AsyncCallback<UserInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }

                    @Override
                    public void onSuccess(UserInfo result) {
                        view.setEmail(result.getEmail());

                        String date = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_LONG)
                                .format(result.getLastLoginTime());
                        view.setLastLoginTime(date);
                    }
                });
    }
}

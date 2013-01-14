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

/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package com.googlecode.fspotcloud.client.main;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.LogoutAction;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.google.common.collect.Lists.newArrayList;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class ClientLoginManager {
    private final Logger log = Logger.getLogger(ClientLoginManager.class.getName());
    private boolean isCalled = false;
    private final List<Runnable> queue = newArrayList();
    private final Runnable callbackHook = new Runnable() {
        @Override
        public void run() {
            for (Runnable task : queue) {
                task.run();
            }

            queue.clear();
        }
    };
    private final DispatchAsync dispatch;
    private final PlaceWhere placeWhere;
    private final PlaceGoTo placeGoTo;
    private UserInfo currentUser;
    private final DataManager dataManager;

    @Inject
    public ClientLoginManager(DispatchAsync dispatch,
                              PlaceWhere placeWhere,
                              PlaceGoTo placeGoTo,
                              DataManager dataManager
                              ) {
        this.dispatch = dispatch;
        this.placeWhere = placeWhere;
        this.placeGoTo = placeGoTo;
        this.dataManager = dataManager;
    }

    public void getUserInfoAsync(final AsyncCallback<UserInfo> callback) {
        if (currentUser != null) {
            callback.onSuccess(currentUser);
        } else {
            queue.add(new Runnable() {
                @Override
                public void run() {
                    callback.onSuccess(currentUser);
                }
            });
            if (!isCalled) {
                dispatch.execute(new GetUserInfo(""), new AsyncCallback<UserInfo>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.WARNING, "Error during getUserInfo ", caught);
                    }

                    @Override
                    public void onSuccess(UserInfo result) {
                        currentUser = result;
                        callbackHook.run();
                    }
                });
            }
        }
    }



    public void logout(AsyncCallback<VoidResult> resultAsyncCallback) {
        dispatch.execute(new LogoutAction(), resultAsyncCallback);
    }

    public void redirectToLogin() {
        getUserInfoAsync(new AsyncCallback<UserInfo>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(UserInfo result) {
                if (!result.isLoggedIn()) {
                    String nextUrl = placeWhere.whereToken();
                    placeGoTo.goTo(new LoginPlace(nextUrl));
                }
            }
        });

    }

    private void reset() {
        queue.clear();
        isCalled = false;
        currentUser = null;
    }

    public void resetApplicationData() {
        reset();
        dataManager.reset();
    }
}

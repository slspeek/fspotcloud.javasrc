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
import com.googlecode.fspotcloud.client.place.LoginPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.place.api.PlaceWhere;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.LogoutAction;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * DOCUMENT ME!
 *
 * @author steven
 */
public class ClientLoginManager implements IClientLoginManager {
    private final Logger log = Logger.getLogger(ClientLoginManager.class.getName());
    private final DispatchAsync dispatch;
    private final PlaceWhere placeWhere;
    private final PlaceGoTo placeGoTo;
    private UserInfo currentUser;
    private final DataManager dataManager;
    private final GetUserInfoMemoProc getUserInfoMemoProc;

    @Inject
    public ClientLoginManager(DispatchAsync dispatch,
                              PlaceWhere placeWhere,
                              PlaceGoTo placeGoTo,
                              DataManager dataManager,
                              GetUserInfoMemoProc getUserInfoMemoProc) {
        this.dispatch = dispatch;
        this.placeWhere = placeWhere;
        this.placeGoTo = placeGoTo;
        this.dataManager = dataManager;
        this.getUserInfoMemoProc = getUserInfoMemoProc;
    }

    @Override
    public void getUserInfoAsync(final AsyncCallback<UserInfo> callback) {
        getUserInfoMemoProc.getAsync(callback);
    }


    @Override
    public void logout(AsyncCallback<VoidResult> resultAsyncCallback) {
        dispatch.execute(new LogoutAction(), resultAsyncCallback);
    }

    @Override
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
                } else {
                    log.log(Level.FINE, "No redirect to login because user is allready logged in.");
                }
            }
        });

    }

    @Override
    public void reset() {
        getUserInfoMemoProc.reset();
    }

    @Override
    public void resetApplicationData() {
        reset();
        dataManager.reset();
    }
}

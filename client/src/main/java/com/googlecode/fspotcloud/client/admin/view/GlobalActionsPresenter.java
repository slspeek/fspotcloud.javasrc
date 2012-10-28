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

package com.googlecode.fspotcloud.client.admin.view;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.admin.view.api.GlobalActionsView;
import com.googlecode.fspotcloud.client.main.view.api.TimerInterface;
import com.googlecode.fspotcloud.shared.dashboard.*;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


public class GlobalActionsPresenter implements GlobalActionsView.GlobalActionsPresenter {
    private final Logger log = Logger.getLogger(GlobalActionsPresenter.class.getName());
    private final GlobalActionsView globalActionsView;
    private final DispatchAsync dispatcher;
    private final TimerInterface timer;

    @Inject
    public GlobalActionsPresenter(GlobalActionsView globalActionsView,
                                  DispatchAsync dispatcher, TimerInterface timer) {
        super();
        this.timer = timer;
        this.globalActionsView = globalActionsView;
        globalActionsView.setPresenter(this);
        this.dispatcher = dispatcher;
    }

    @Override
    public void deleteAllCommands() {
        globalActionsView.getDeleteAllCommandsButton().setEnabled(false);
        dispatcher.execute(new UserDeletesAllCommandsAction(),
                new AsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "Action Exception ", caught);
                        globalActionsView.getDeleteAllCommandsButton()
                                .setEnabled(true);
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        globalActionsView.getDeleteAllCommandsButton()
                                .setEnabled(true);
                    }
                });
    }

    @Override
    public void deleteAllTags() {
        globalActionsView.getDeleteAllTagsButton().setEnabled(false);

        if (globalActionsView.confirm("Really delete all tags and photos?")) {
            dispatcher.execute(new UserDeletesAllAction(),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            log.log(Level.SEVERE, "Action Exception ", caught);
                            globalActionsView.getDeleteAllTagsButton()
                                    .setEnabled(true);
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            globalActionsView.getDeleteAllTagsButton()
                                    .setEnabled(true);
                        }
                    });
        } else {
            globalActionsView.getDeleteAllTagsButton().setEnabled(true);
        }
    }

    @Override
    public void update() {
        log.info("update");
        globalActionsView.getUpdateButton().setEnabled(false);
        dispatcher.execute(new UserSynchronizesPeerAction(),
                new AsyncCallback<VoidResult>() {
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "Action Exception ", caught);
                        globalActionsView.getUpdateButton().setEnabled(true);
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        log.info("succes update");
                        globalActionsView.getUpdateButton().setEnabled(true);
                    }
                });
    }

    @Override
    public void init() {
        globalActionsView.setPresenter(this);
        log.info("init");
        getMetaData();
    }

    private void getMetaData() {
        dispatcher.execute(new GetMetaDataAction(),
                new AsyncCallback<GetMetaDataResult>() {
                    @Override
                    public void onSuccess(GetMetaDataResult meta) {
                        populateView(meta);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "Action Exception from remote: ",
                                caught);
                        timer.setRunnable(new Runnable() {
                            @Override
                            public void run() {
                                getMetaData();
                            }
                        });
                        timer.schedule(6000);
                    }
                });
    }

    private void populateView(GetMetaDataResult info) {
        log.info("populate");
        globalActionsView.getLastSeenPeerValue()
                .setText(String.valueOf(info.getPeerLastSeen()));
        globalActionsView.getPhotoCountOnPeerValue()
                .setText(String.valueOf(info.getPeerPhotoCount()));
        globalActionsView.getTagCountValue()
                .setText(String.valueOf(info.getTagCount()));
        globalActionsView.getPendingCommandCountValue()
                .setText(String.valueOf(info.getPendingCommandCount()));

        timer.setRunnable(new Runnable() {
            @Override
            public void run() {
                getMetaData();
            }
        });
        timer.schedule(7000);
    }
}

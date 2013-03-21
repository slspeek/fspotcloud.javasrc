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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardActions;
import com.googlecode.fspotcloud.client.main.view.api.PeerActionsView;
import com.googlecode.fspotcloud.keyboardaction.KeyboardActionEvent;
import com.googlecode.fspotcloud.keyboardaction.TimerInterface;
import com.googlecode.fspotcloud.shared.dashboard.GetMetaDataAction;
import com.googlecode.fspotcloud.shared.dashboard.GetMetaDataResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


public class PeerActionsPresenter implements PeerActionsView.PeerActionsPresenter {
    private final Logger log = Logger.getLogger(PeerActionsPresenter.class.getName());
    private final PeerActionsView peerActionsView;
    private final DispatchAsync dispatcher;
    private final TimerInterface timer;
    private final EventBus eventBus;
    private final DashboardActions actions;

    private String lastTreeHash = "";

    @Inject
    public PeerActionsPresenter(PeerActionsView peerActionsView,
                                DispatchAsync dispatcher,
                                TimerInterface timer,
                                EventBus eventBus,
                                DashboardActions actions) {
        super();
        this.timer = timer;
        this.peerActionsView = peerActionsView;
        this.eventBus = eventBus;
        this.actions = actions;
        peerActionsView.setPresenter(this);
        this.dispatcher = dispatcher;
    }


    @Override
    public void init() {
        peerActionsView.setPresenter(this);
        log.info("init");
        getMetaData();
    }

    @Override
    public void stop() {
        timer.cancel();
    }

    private void getMetaData() {
        dispatcher.execute(new GetMetaDataAction(),
                new AsyncCallback<GetMetaDataResult>() {
                    @Override
                    public void onSuccess(GetMetaDataResult meta) {
                        processMetaData(meta);
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

    private void processMetaData(GetMetaDataResult meta) {
        populateView(meta);
        String newHash = meta.getAdminTreeHash();
        if (!newHash.equals(lastTreeHash)) {
            lastTreeHash = newHash;
            eventBus.fireEvent(new KeyboardActionEvent(actions.reloadTree.getId()));
        }
    }

    private void populateView(GetMetaDataResult info) {
        log.info("populate");
        peerActionsView.getLastSeenPeerValue()
                .setText(String.valueOf(info.getPeerLastSeen()));
        peerActionsView.getPhotoCountOnPeerValue()
                .setText(String.valueOf(info.getPeerPhotoCount()));
        peerActionsView.getTagCountValue()
                .setText(String.valueOf(info.getTagCount()));
        peerActionsView.getPendingCommandCountValue()
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

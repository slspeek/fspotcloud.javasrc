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

package com.googlecode.fspotcloud.server.admin.integration;

import com.googlecode.botdispatch.SerializableAsyncCallback;
import com.googlecode.botdispatch.controller.dispatch.ControllerDispatchAsync;
import com.googlecode.fspotcloud.peer.db.Data;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.PhotoDao;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.shared.dashboard.UserImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.UserSynchronizesPeerAction;
import com.googlecode.fspotcloud.shared.dashboard.UserUnImportsTagAction;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import net.customware.gwt.dispatch.server.Dispatch;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.testng.AssertJUnit.fail;

public class PeerServerEnvironment {
    static final Logger log = Logger.getLogger(PeerServerIntegrationTest.class.getName());
    @Inject
    PhotoDao photoDao;
    @Inject
    protected TagDao tagDao;
    @Inject
    ControllerDispatchAsync controller;
    @Inject
    PeerDatabaseDao peers;
    @Inject
    Data data;
    @Inject
    Dispatch dispatch;
    @Inject
    PhotoAssert photoInfo;
    @Inject
    TagAssert tagInfo;
    @Inject
    PeerDatabaseAssert peerInfo;

    protected TagTreeResult fetchTagTree() throws DispatchException {
        return dispatch.execute(new GetTagTreeAction());
    }

    protected void setUpPeer() throws SQLException {
        setPeerTestDatabase("photos.db");
    }

    protected void verifyAllTagsAreLoaded() {
        tagInfo.assertTagsLoaded("1", "2", "3", "4", "5");
    }

    protected void verifiyFurnitureFirstPhaseWasRemoved() {
        photoInfo.assertPhotosRemoved("12", "13", "4", "5", "15");
    }

    protected void verifyImagesWereRemoved() {
        photoInfo.assertPhotosRemoved("6");
        photoInfo.assertPhotosRemoved("16");
        photoInfo.assertPhotosRemoved("14");
        photoInfo.assertPhotosRemoved("7");
    }

    protected void verfiyFurnitureFirstPhaseIsLoaded() {
        photoInfo.assertPhotosLoaded("12", "13", "4", "5", "15");
    }

    protected void verfiyFurnitureIsLoaded() {
        photoInfo.assertPhotosLoaded("12", "13", "4", "5", "15");
        photoInfo.assertPhotosLoaded("7", "6", "16", "14");
    }

    protected void assertPCLoaded() {
        photoInfo.assertPhotosLoaded("17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28", "9", "11");
    }

    protected void assertComputersIsLoaded() {
        photoInfo.assertPhotosLoaded("17", "18", "19", "20", "21", "22", "23",
                "24", "25", "26", "27", "28");
    }

    protected void setPeerTestDatabase(String db) throws SQLException {
        String basedir = (new File(".")).getAbsolutePath();
        File testDatabase = new File(basedir + "./peer/src/test/resources/" +
                db);
        String path = testDatabase.getPath();
        log.info("DBPath " + path);

        if (data != null) {
            data.setJDBCUrl("jdbc:sqlite:" + path);
        }
    }

    protected void synchronizePeer() {
        controller.execute(new UserSynchronizesPeerAction(),
                new SerializableAsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.log(Level.SEVERE, "On fail ", caught);
                        fail();
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        log.info("On success");
                    }
                });
    }

    protected void importTag(String tagId) {
        controller.execute(new UserImportsTagAction(tagId),
                new SerializableAsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.info("On fail " + caught);
                        fail();
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        log.info("On success");
                    }
                });
    }

    protected void unImportTag(String tagId) {
        controller.execute(new UserUnImportsTagAction(tagId),
                new SerializableAsyncCallback<VoidResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        log.info("On fail " + caught);
                        fail();
                    }

                    @Override
                    public void onSuccess(VoidResult result) {
                        log.info("On success");
                    }
                });
    }

    public void testImportAllTags() throws Exception {
        setUpPeer();
        synchronizePeer();
        verifyAllTagsAreLoaded();
    }
}

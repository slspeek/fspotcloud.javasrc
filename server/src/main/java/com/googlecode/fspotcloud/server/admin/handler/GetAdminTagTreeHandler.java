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

package com.googlecode.fspotcloud.server.admin.handler;

import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.server.model.tag.TreeBuilder;
import com.googlecode.fspotcloud.shared.dashboard.GetAdminTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import com.googlecode.fspotcloud.user.IAdminPermission;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;


public class GetAdminTagTreeHandler extends SimpleActionHandler<GetAdminTagTreeAction, TagTreeResult> {
    @SuppressWarnings("unused")
    @Inject
    private Logger log;
    private final TagDao tagManager;
    private final IAdminPermission adminPermission;
    private final PeerDatabaseDao peerDatabaseDao;

    @Inject
    public GetAdminTagTreeHandler(TagDao tagManager,
                                  IAdminPermission adminPermission,
                                  PeerDatabaseDao peerDatabaseDao) {
        this.tagManager = tagManager;
        this.adminPermission = adminPermission;
        this.peerDatabaseDao = peerDatabaseDao;
    }

    @Override
    public TagTreeResult execute(GetAdminTagTreeAction action,
                                 ExecutionContext context) throws DispatchException {
        adminPermission.checkAdminPermission();
        return new TagTreeResult(getFullTree());
    }

    private TagNode getFullTree() {
        PeerDatabase p = peerDatabaseDao.get();

        if (p.getCachedAdminTagTree() != null) {
            log.info("Got the admin tree from cache HIT");

            return p.getCachedAdminTagTree();
        } else {
            log.info("Missed the cache; building admin");
            List<TagNode> tags = tagManager.getTags();
            TreeBuilder builder = new TreeBuilder(tags);
            TagNode tree = builder.getRoots();
            p.setCachedAdminTagTree(tree);
            log.info("Builded, about to save");
            peerDatabaseDao.save(p);
            return tree;
        }
    }
}

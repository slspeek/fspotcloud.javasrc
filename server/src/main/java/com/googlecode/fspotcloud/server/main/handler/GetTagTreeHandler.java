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

package com.googlecode.fspotcloud.server.main.handler;

import com.googlecode.fspotcloud.server.model.api.PeerDatabase;
import com.googlecode.fspotcloud.server.model.api.PeerDatabaseDao;
import com.googlecode.fspotcloud.server.model.api.TagDao;
import com.googlecode.fspotcloud.server.model.tag.IUserGroupHelper;
import com.googlecode.fspotcloud.server.model.tag.TreeBuilder;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagNode;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import com.googlecode.fspotcloud.user.UserService;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.server.SimpleActionHandler;
import net.customware.gwt.dispatch.shared.DispatchException;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;


public class GetTagTreeHandler extends SimpleActionHandler<GetTagTreeAction, TagTreeResult> {
    private final Logger log = Logger.getLogger(GetTagTreeHandler.class.getName());
    private final PeerDatabaseDao peerDatabaseDao;
    private final TagDao tagManager;
    private final IUserGroupHelper userGroupHelper;
    private final UserService userService;

    @Inject
    public GetTagTreeHandler(PeerDatabaseDao peerDatabaseDao,
                             TagDao tagManager, IUserGroupHelper userGroupHelper,
                             UserService userService) {
        this.peerDatabaseDao = peerDatabaseDao;
        this.tagManager = tagManager;
        this.userGroupHelper = userGroupHelper;
        this.userService = userService;
    }

    @Override
    public TagTreeResult execute(GetTagTreeAction action,
                                 ExecutionContext context) throws DispatchException {
        List<TagNode> subTree;
        List<TagNode> fullTree = getImportIssuedTree();

        if (userService.isUserAdmin()) {
            subTree = fullTree;
        } else {
            Set<String> visibleTags = userGroupHelper.getVisibleTagIds();
            TagTreeHelper helper = new TagTreeHelper(fullTree, visibleTags);
            subTree = helper.getSubTree();
        }

        return new TagTreeResult(subTree);
    }

    private List<TagNode> getImportIssuedTree() {
        PeerDatabase p = peerDatabaseDao.get();

        if (p.getCachedTagTree() != null) {
            log.info("Got the tree from cache HIT");

            return p.getCachedTagTree();
        } else {
            log.info("Missed the cache; building");

            List<TagNode> tags = tagManager.getTags();
            TreeBuilder builder = new TreeBuilder(tags);
            List<TagNode> tree = builder.getPublicRoots();
            p.setCachedTagTree(tree);
            log.info("Builded, about to save");
            peerDatabaseDao.save(p);

            return tree;
        }
    }
}

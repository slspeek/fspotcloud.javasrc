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

package com.googlecode.fspotcloud.client.data;

import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


public class IndexingUtil {
    @SuppressWarnings("unused")
    private final Logger log = Logger.getLogger(IndexingUtil.class.getName());

    public void rebuildTagNodeIndex(Map<String, TagNode> tagNodeIndex,
                                    List<TagNode> tagTreeData) {
        tagNodeIndex.clear();

        for (TagNode root : tagTreeData) {
            addTagNodeIndex(tagNodeIndex, root);
        }
    }

    private void addTagNodeIndex(Map<String, TagNode> tagNodeIndex, TagNode node) {
        tagNodeIndex.put(node.getId(), node);

        for (TagNode child : node.getChildren()) {
            addTagNodeIndex(tagNodeIndex, child);
        }
    }
}

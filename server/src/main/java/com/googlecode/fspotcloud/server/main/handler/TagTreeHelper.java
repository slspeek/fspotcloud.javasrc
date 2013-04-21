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

import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static com.googlecode.fspotcloud.server.model.tag.TreeBuilder.sortTree;

public class TagTreeHelper {
    private TagNode fullTree;
    private Set<String> subSet;
    private Map<String, TagNode> index = newHashMap();

    public TagTreeHelper(TagNode fullTree, Set<String> subSet) {
        this.fullTree = fullTree;
        this.subSet = subSet;
    }

    private void buildIndex() {
        index(fullTree);
    }

    private void index(TagNode tagNode) {
        String tagId = tagNode.getId();
        if (tagId != null) {
            index.put(tagNode.getId(), tagNode);
        }
        for (TagNode child : tagNode.getChildren()) {
            index(child);
        }
    }

    public TagNode getSubTree() {
        buildIndex();

        TagNode root = new TagNode();
        Set<TagNode> selected = subSetNodes();

        for (TagNode unconnected : selected) {
            TagNode parent = getParentInSelection(unconnected);

            if (parent == null) {

                root.addChild(unconnected);
            } else {
                TagNode parentInSelected = find(parent, selected);
                parentInSelected.addChild(unconnected);
            }
        }
        sortTree(root);
        return root;
    }

    private TagNode find(TagNode parent, Set<TagNode> selected) {
        String id = parent.getId();
        for (TagNode node : selected) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    private TagNode getParentInSelection(TagNode node) {
        String id = node.getId();
        node = index.get(id);

        if ("0".equals(node.getParentId())) {
            return null;
        } else {
            TagNode parent;

            while ((parent = index.get(node.getParentId())) != null) {
                if (subSet.contains(parent.getId())) {


                    return parent;
                } else {
                    node = parent;
                }
            }

            return null;
        }
    }

    Set<TagNode> subSetNodes() {
        Set<TagNode> nodeSet = newHashSet();

        for (String tagId : subSet) {
            if (index.containsKey(tagId)) {
                final TagNode e = index.get(tagId);
                nodeSet.add(new TagNode(e));
            }
        }

        return nodeSet;
    }
}

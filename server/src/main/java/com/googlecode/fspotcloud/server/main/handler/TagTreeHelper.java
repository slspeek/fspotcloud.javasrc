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

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

public class TagTreeHelper {
    private List<TagNode> fullTree;
    private Set<String> subSet;
    private Map<String, TagNode> index = newHashMap();

    public TagTreeHelper(List<TagNode> fullTree, Set<String> subSet) {
        this.fullTree = fullTree;
        this.subSet = subSet;
    }

    private void buildIndex() {
        for (TagNode root : fullTree) {
            index(root);
        }
    }

    private void index(TagNode tagNode) {
        index.put(tagNode.getId(), tagNode);

        for (TagNode child : tagNode.getChildren()) {
            index(child);
        }
    }

    public List<TagNode> getSubTree() {
        buildIndex();

        List<TagNode> roots = newArrayList();
        Set<TagNode> selected = subSetNodes();

        for (TagNode unconnected : selected) {
            TagNode parent = getParentInSelection(unconnected);

            if (parent == null) {

                roots.add(unconnected);
            } else {
                TagNode parentInSelected = find(parent, selected);
                parentInSelected.addChild(unconnected);
            }
        }

        return roots;
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

        if (/*node.getParentId() == null || */"0".equals(node.getParentId())) {
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
            final TagNode e = index.get(tagId);
            nodeSet.add(new TagNode(e));
        }

        return nodeSet;
    }
}

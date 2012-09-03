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

package com.googlecode.fspotcloud.server.model.tag;

import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TreeBuilder {
    private List<TagNode> flatNodes;
    private Map<String, TagNode> index;

    public TreeBuilder(List<TagNode> flatNodes) {
        this.flatNodes = flatNodes;
    }

    public List<TagNode> getRoots() {
        return getFilteredRoots(new Filter() {
            @Override
            public boolean isValid(TagNode node) {
                return true;
            }
        });
    }

    private void buildMap() {
        index = new HashMap<String, TagNode>();

        for (TagNode node : flatNodes) {
            String name = node.getId();
            index.put(name, node);
        }
    }

    public List<TagNode> getPublicRoots() {
        return getFilteredRoots(new Filter() {
            @Override
            public boolean isValid(TagNode node) {
                return node.isImportIssued();
            }
        });
    }

    private List<TagNode> getFilteredRoots(Filter f) {
        buildMap();

        List<TagNode> roots = new ArrayList<TagNode>();

        for (TagNode node : flatNodes) {
            if (f.isValid(node)) {
                TagNode parent = getFilteredParent(node, f);

                if (parent == null) {
                    roots.add(node);
                } else {
                    parent.addChild(node);
                }
            }
        }

        return roots;
    }

    private TagNode getFilteredParent(TagNode node, Filter filter) {
        if ("0".equals(node.getParentId())) {
            return null;
        } else {
            TagNode parent;

            while ((parent = index.get(node.getParentId())) != null) {
                if (filter.isValid(parent)) {
                    return parent;
                } else {
                    node = parent;
                }
            }

            return null;
        }
    }
}

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

import com.google.gwt.cell.client.Cell;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.TreeViewModel;
import com.googlecode.fspotcloud.shared.main.TagNode;

import javax.inject.Provider;
import java.util.List;
import java.util.logging.Logger;


public class TagTreeModel implements TreeViewModel {
    private final Logger log = Logger.getLogger(TagTreeModel.class.getName());
    List<TagNode> roots;
    SelectionModel<TagNode> selectionModel;
    Provider<Cell<TagNode>> cellProvider;

    public TagTreeModel(List<TagNode> roots,
                        SelectionModel<TagNode> selectionModel,
                        Provider<Cell<TagNode>> cellProvider) {
        super();
        this.roots = roots;
        this.selectionModel = selectionModel;
        this.cellProvider = cellProvider;
    }

    @Override
    public <T> NodeInfo<?> getNodeInfo(T value) {
        if (value == null) {
            ListDataProvider<TagNode> rootNodes = new ListDataProvider<TagNode>(roots);

            return new DefaultNodeInfo<TagNode>(rootNodes, cellProvider.get(),
                    selectionModel, null);
        } else if (value instanceof TagNode) {
            TagNode node = (TagNode) value;
            List<TagNode> children = node.getChildren();
            ListDataProvider<TagNode> childNodes = new ListDataProvider<TagNode>(children);

            return new DefaultNodeInfo<TagNode>(childNodes, cellProvider.get(),
                    selectionModel, null);
        } else {
            log.warning("getNodeInfo called with non-TagNode value: " + value);
        }

        return null;
    }

    @Override
    public boolean isLeaf(Object value) {
        if (value == null) {
            return false;
        } else if (value instanceof TagNode) {
            TagNode node = (TagNode) value;
            List<TagNode> children = node.getChildren();
            boolean hasNoChildren = children.isEmpty();

            return hasNoChildren;
        } else {
            log.warning("isLeaf called with non-TagNode value: " + value);
        }

        return false;
    }
}

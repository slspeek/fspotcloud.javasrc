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
import com.google.gwt.view.client.TreeViewModel.NodeInfo;
import com.googlecode.fspotcloud.client.main.DispatchAsyncTestImpl;
import com.googlecode.fspotcloud.shared.main.TagNode;
import junit.framework.TestCase;

import javax.inject.Provider;
import java.util.List;


public class TagTreeModelTest extends TestCase {
    public List<TagNode> data;
    private TagTreeModel model;

    public TagTreeModelTest() {
        DispatchAsyncTestImpl service = new DispatchAsyncTestImpl();
        data = service.initData();
    }

    protected void setUp() throws Exception {
        super.setUp();
        model = new TagTreeModel(data, null,
                new Provider<Cell<TagNode>>() {
                    @Override
                    public Cell<TagNode> get() {
                        return new TagCell();
                    }
                });
    }

    public void testGetNodeInfo() {
        NodeInfo<TagNode> roots = (NodeInfo<TagNode>) model.getNodeInfo(null);
    }

    public void testIsLeaf() {
        assertFalse(model.isLeaf(null));
    }
}

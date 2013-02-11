package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.TreeNode;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.ui.Resources;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.TagPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.shared.main.TagNode;

import javax.inject.Provider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class TreePresenterBase implements TreeView.TreePresenter {
    private Logger log = Logger.getLogger(TreePresenterBase.class.getName());
    
    protected final TreeView treeView;
    protected final DataManager dataManager;
    protected final SingleSelectionModel<TagNode> selectionModel;
  
    protected TagPlace place;
    protected TagTreeModel treeModel;
    protected TagNode model;

    protected TreePresenterBase(TreeView treeView,
                                DataManager dataManager,
                                SingleSelectionModel<TagNode> selectionModel) {
        this.treeView = treeView;
        this.dataManager = dataManager;
        this.selectionModel = selectionModel;
    }


    public void init() {
        log.log(Level.FINE, "Tree presenter base init");
        reloadTree();
    }

    protected void setModel(TagNode root) {
        model = root;
        treeModel = new TagTreeModel(root, selectionModel,
                new Provider<Cell<TagNode>>() {
                    @Override
                    public Cell<TagNode> get() {
                        return new AdminTagCell();
                    }
                });
        treeView.setTreeModel(treeModel);
        log.log(Level.FINE, "setModel before updatePlace. With model:" + model);
        updatePlace();
    }

   

    public void reloadTree() {
        requestTagTreeData();
    }

    protected abstract void requestTagTreeData();

    protected void openSelectedTreeNode(TreeNode rootTreeNode, TagNode tagNode) {
        List<Integer> path = tagNode.pathTo(model);
        log.log(Level.FINE, "path to root:" + path + " " + model);
        TreeNode current = rootTreeNode;
        for (Integer index : path) {
            TreeNode child = current.setChildOpen(index, true);
            if (child == null) {
                log.log(Level.FINE, "node: " + current + " has not returned child " + index);
                break;
            } else {
                current = child;
            }
        }
    }

    protected void updatePlace() {
        if (place != null && model != null) {
            log.log(Level.FINE, "Update place with non-trivial place " + place);

            TagNode selectedNode = model.findByTagId(place.getTagId());
            selectionModel.setSelected(selectedNode, true);
            TreeNode root = treeView.getRootNode();
            log.log(Level.FINE, "tree model root and selected node " + root + " " + selectedNode);
            if (root != null) {
                openSelectedTreeNode(root, selectedNode);
            }
        } else {
            log.log(Level.FINE, "place is null");
        }
    }
}

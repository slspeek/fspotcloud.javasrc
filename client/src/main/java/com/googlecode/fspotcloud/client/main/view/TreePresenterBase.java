package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.TreeNode;
import com.googlecode.fspotcloud.client.data.DataManager;
import com.googlecode.fspotcloud.client.main.view.api.ITreeSelectionHandler;
import com.googlecode.fspotcloud.client.main.view.api.TreeView;
import com.googlecode.fspotcloud.client.place.DashboardPlace;
import com.googlecode.fspotcloud.shared.main.TagNode;

import javax.inject.Provider;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class TreePresenterBase implements TreeView.TreePresenter, Provider<Cell<TagNode>> {
    private Logger log = Logger.getLogger(TreePresenterBase.class.getName());

    protected final TreeView treeView;
    protected final DataManager dataManager;
    protected final SingleSelectionModelExt selectionModel;
    protected final ITreeSelectionHandler treeSelectionHandler;

    protected DashboardPlace place;
    protected TagTreeModel treeModel;
    protected TagNode model;
    protected boolean firstRun = true;

    protected TreePresenterBase(TreeView treeView,
                                DataManager dataManager,
                                SingleSelectionModelExt selectionModel,
                                ITreeSelectionHandler treeSelectionHandler) {
        this.treeView = treeView;
        this.dataManager = dataManager;
        this.selectionModel = selectionModel;
        this.treeSelectionHandler = treeSelectionHandler;
    }


    public void init() {
        log.log(Level.FINE, "Tree presenter base init");
        treeSelectionHandler.setSelectionModel(selectionModel);
        reloadTree();
    }

    protected void setModel(TagNode root) {
        model = root;
        treeModel = new TagTreeModel(root, selectionModel, this
        );
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
            log.log(Level.FINE, "Update place with non-trivial place and model has been set" + place);

            TagNode selectedNode = model.findByTagId(place.getTagId());
            TreeNode root = treeView.getRootNode();
            log.log(Level.FINE, "tree model root and selected node " + root + " " + selectedNode);
            if (root != null) {
                if (selectedNode != null) {
                    openSelectedTreeNode(root, selectedNode);
                    if (firstRun) {
                        firstRun = false;
                        treeSelectionHandler.setIgnoreNext(true);
                    }
                    selectionModel.setSelected(selectedNode, true);
                } else {
                    if (place.getTagId().equals("all")) {
                        TagNode selected = selectionModel.getSelectedObject();
                        log.log(Level.FINE, "all " + selected);
                        if (selected != null) {
                            selectionModel.setSelected(selected, false);
                        }

                    }
                }
            }
        } else {
            log.log(Level.FINE, "place or model is null");
        }
    }

    @Override
    abstract public Cell<TagNode> get();

}

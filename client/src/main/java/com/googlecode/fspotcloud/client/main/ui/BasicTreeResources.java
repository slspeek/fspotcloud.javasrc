package com.googlecode.fspotcloud.client.main.ui;

import com.google.gwt.user.cellview.client.CellTree;

public interface BasicTreeResources extends CellTree.Resources {
	@Source("basiccelltree.css")
	public MyStyle cellTreeStyle();

}

interface MyStyle extends CellTree.Style {
	String foo();
}
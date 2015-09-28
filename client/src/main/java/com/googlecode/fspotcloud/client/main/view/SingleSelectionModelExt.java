package com.googlecode.fspotcloud.client.main.view;

import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.fspotcloud.shared.main.TagNode;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SingleSelectionModelExt extends SingleSelectionModel<TagNode> {

	private Logger log = Logger.getLogger(SingleSelectionModelExt.class
			.getName());

	public void setSelected(final TagNode node, final boolean selected) {
		log.log(Level.FINE, "set selected");
		super.setSelected(node, selected);
		log.log(Level.FINE, "after super set selected");
	}

}

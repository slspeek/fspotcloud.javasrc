package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.resources.client.ImageResource;

@GwtCompatible
public class ActionUIDef {

	private final String id, name, description;
	private final ImageResource icon;

	public ActionUIDef(String id, String name, String description) {
		this(id, name, description, null);
	}

	public ActionUIDef(String id, String name, String description,
			ImageResource icon) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ImageResource getIcon() {
		return icon;
	}
}

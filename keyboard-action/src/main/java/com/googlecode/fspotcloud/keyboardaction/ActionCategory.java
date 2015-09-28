package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;

import java.util.List;

import static com.google.common.base.Objects.equal;
import static com.google.common.collect.Lists.newArrayList;

@GwtCompatible
public class ActionCategory {
	private final String name;
	private final List<ActionUIDef> actions = newArrayList();

	ActionCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	void add(ActionUIDef action) {
		actions.add(action);
	}

	public List<ActionUIDef> getActions() {
		return actions;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ActionCategory) {
			ActionCategory actionCategory = (ActionCategory) o;
			return equal(name, actionCategory.name)
					&& equal(actions, actionCategory.actions);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, actions);
	}
}

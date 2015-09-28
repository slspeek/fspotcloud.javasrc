package com.googlecode.fspotcloud.client.place;

import com.google.common.base.Objects;
import com.googlecode.fspotcloud.client.place.api.Navigator;

import static com.google.common.base.Objects.equal;

public class Move {

	private final Navigator.Direction direction;
	private final Navigator.Unit unit;

	public Move(Navigator.Direction direction, Navigator.Unit unit) {
		this.direction = direction;
		this.unit = unit;
	}

	public Navigator.Direction getDirection() {
		return direction;
	}

	public Navigator.Unit getUnit() {
		return unit;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Move) {
			Move move = (Move) o;
			return equal(direction, move.direction) && equal(unit, move.unit);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(direction, unit);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("direction", direction)
				.add("unit", unit).toString();
	}
}

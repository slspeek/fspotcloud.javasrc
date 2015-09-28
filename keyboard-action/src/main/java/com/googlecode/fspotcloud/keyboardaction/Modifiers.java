package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.base.Objects;

public class Modifiers {
	private final boolean shift, ctrl, alt;
	public static final Modifiers NONE = new Modifiers(false, false, false);
	public static final Modifiers SHIFT = new Modifiers(true, false, false);
	public static final Modifiers SHIFT_CTRL = new Modifiers(true, true, false);
	public static final Modifiers CTRL = new Modifiers(false, true, false);
	public static final Modifiers ALT = new Modifiers(false, false, true);

	public Modifiers(boolean shift, boolean ctrl, boolean alt) {
		this.shift = shift;
		this.ctrl = ctrl;
		this.alt = alt;
	}

	public boolean isShift() {
		return shift;
	}

	public boolean isCtrl() {
		return ctrl;
	}

	public boolean isAlt() {
		return alt;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(shift, ctrl, alt);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Modifiers) {
			Modifiers modifiers = (Modifiers) obj;
			return modifiers.shift == shift && modifiers.ctrl == ctrl
					&& modifiers.alt == alt;
		}
		return false;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("shift", shift)
				.add("ctrl", ctrl).add("alt", alt).toString();
	}
}

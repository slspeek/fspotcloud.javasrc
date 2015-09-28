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

package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.gwt.event.dom.client.KeyCodes;

@GwtCompatible
public class KeyStroke {

	public static final int KEY_NUM_PAD_PLUS = 107;
	private static final int KEY_NUM_PAD_TIMES = 106;
	public static final int KEY_NUM_PAD_MINUS = 109;
	public static final int KEY_FORWARD_SLASH = 191;
	public static final int KEY_SPACE = 32;
	public static final int KEY_PAUSE = 19;
	public static final int KEY_INSERT = 45;

	public static final KeyStroke DELETE = plain(KeyCodes.KEY_DELETE);
	public static final KeyStroke A = plain('A');
	public static final KeyStroke F = plain('F');
	public static final KeyStroke H = plain('H');
	public static final KeyStroke I = plain('I');
	public static final KeyStroke J = plain('J');
	public static final KeyStroke K = plain('K');
	public static final KeyStroke L = plain('L');
	public static final KeyStroke M = plain('M');
	public static final KeyStroke R = plain('R');
	public static final KeyStroke X = plain('X');
	public static final KeyStroke FORWARD_SLASH = plain(KEY_FORWARD_SLASH);
	public static final KeyStroke NUM_PAD_TIMES = plain(KEY_NUM_PAD_TIMES);
	public static final KeyStroke NUM_PAD_PLUS = plain(KEY_NUM_PAD_PLUS);
	public static final KeyStroke NUM_PAD_MINUS = plain(KEY_NUM_PAD_MINUS);
	public static final KeyStroke ESC = plain(KeyCodes.KEY_ESCAPE);
	public static final KeyStroke INSERT = plain(KEY_INSERT);
	public static final KeyStroke PAUSE = plain(KEY_PAUSE);
	public static final KeyStroke ENTER = plain(KeyCodes.KEY_ENTER);
	public static final KeyStroke HOME = plain(KeyCodes.KEY_HOME);
	public static final KeyStroke LEFT = plain(KeyCodes.KEY_LEFT);
	public static final KeyStroke RIGHT = plain(KeyCodes.KEY_RIGHT);
	public static final KeyStroke UP = plain(KeyCodes.KEY_UP);
	public static final KeyStroke DOWN = plain(KeyCodes.KEY_DOWN);
	public static final KeyStroke SPACE = plain(KEY_SPACE);

	private final Modifiers modifiers;
	private final int keyCode;
	public static final KeyStroke END = plain(KeyCodes.KEY_END);
	public static final KeyStroke PAGEDOWN = plain(KeyCodes.KEY_PAGEDOWN);
	public static final KeyStroke PAGEUP = plain(KeyCodes.KEY_PAGEUP);
	public static final KeyStroke N = plain('N');
	public static final KeyStroke P = plain('P');
	public static final KeyStroke U = plain('U');
	public static final KeyStroke B = plain('B');

	public static KeyStroke shift(int keyCode) {
		return new KeyStroke(Modifiers.SHIFT, keyCode);
	}

	public static KeyStroke plain(int keyCode) {
		return new KeyStroke(keyCode);
	}

	public static KeyStroke ctrl(int keyCode) {
		return new KeyStroke(Modifiers.CTRL, keyCode);
	}

	public static KeyStroke alt(int keyCode) {
		return new KeyStroke(Modifiers.ALT, keyCode);
	}

	public static KeyStroke alt(KeyStroke stroke) {
		return new KeyStroke(Modifiers.ALT, stroke.getKeyCode());
	}

	public KeyStroke(Modifiers modifiers, int keyCode) {
		this.modifiers = modifiers;
		this.keyCode = keyCode;
	}

	public KeyStroke(int keyCode) {
		this.keyCode = keyCode;
		this.modifiers = Modifiers.NONE;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public String getKeyString() {
		String result;

		switch (keyCode) {

			case KeyCodes.KEY_LEFT :
				result = "Left arrow";

				break;

			case KeyCodes.KEY_RIGHT :
				result = "Right arrow";

				break;

			case KeyCodes.KEY_UP :
				result = "Up arrow";

				break;

			case KeyCodes.KEY_DOWN :
				result = "Down arrow";

				break;

			case KeyCodes.KEY_ENTER :
				result = "Enter";

				break;

			case KeyCodes.KEY_END :
				result = "End";

				break;

			case KeyCodes.KEY_HOME :
				result = "Home";

				break;

			case KeyCodes.KEY_PAGEDOWN :
				result = "Page Down";

				break;

			case KeyCodes.KEY_PAGEUP :
				result = "Page Up";

				break;

			case KeyCodes.KEY_ESCAPE :
				result = "Esc";

				break;

			case KeyCodes.KEY_SHIFT :
				result = "Shift";

				break;

			case KEY_SPACE :
				result = "Space";

				break;

			case KEY_PAUSE :
				result = "Pause";

				break;

			case KEY_NUM_PAD_MINUS :
				result = "Numpad -";

				break;

			case KEY_NUM_PAD_PLUS :
				result = "Numpad +";

				break;

			case KEY_NUM_PAD_TIMES :
				result = "Numpad *";

				break;

			case KEY_FORWARD_SLASH :
				result = "/";
				break;

			case KeyCodes.KEY_CTRL :
				result = "Control";

				break;
			case KeyCodes.KEY_DELETE :
				result = "Delete";

				break;
			case KEY_INSERT :
				result = "Insert";

				break;
			default :
				result = String.valueOf((char) keyCode);

				break;
		}

		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(modifiers, keyCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof KeyStroke) {
			KeyStroke keyStroke = (KeyStroke) obj;
			return Objects.equal(keyStroke.keyCode, keyCode)
					&& Objects.equal(keyStroke.modifiers, modifiers);
		}
		return false;
	}

	public String toString() {
		return "" + (modifiers.isShift() ? "SHIFT-" : "")
				+ (modifiers.isAlt() ? "ALT-" : "")
				+ (modifiers.isCtrl() ? "CTRL-" : "") + getKeyString();
	}
}

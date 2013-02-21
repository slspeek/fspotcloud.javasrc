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
    public static final int KEY_NUM_PAD_MINUS = 109;
    public static final int KEY_FORWARD_SLASH = 191;
    public static final int KEY_SPACE = 32;
    public static final int KEY_PAUSE = 19;
    public static final int KEY_INSERT = 45;
    public static final KeyStroke DELETE = new KeyStroke(KeyCodes.KEY_DELETE);
    public static final KeyStroke H = new KeyStroke('H');
    public static final KeyStroke I = new KeyStroke('I');
    public static final KeyStroke J = new KeyStroke('J');
    public static final KeyStroke K = new KeyStroke('K');
    public static final KeyStroke R = new KeyStroke('R');
    public static KeyStroke ESC = new KeyStroke(KeyCodes.KEY_ESCAPE);
    public static KeyStroke INSERT = new KeyStroke(KEY_INSERT);
    public static KeyStroke PAUSE = new KeyStroke(KEY_PAUSE);

    private final Modifiers modifiers;
    private final int keyCode;
    public static final KeyStroke X = new KeyStroke('X');
    public static final KeyStroke A = new KeyStroke('A');
    public static final KeyStroke ENTER = new KeyStroke(KeyCodes.KEY_ENTER);
    public static final KeyStroke M = new KeyStroke('M');

    public static KeyStroke shift(int keyCode) {
        return new KeyStroke(Modifiers.SHIFT, keyCode);
    }

    public static KeyStroke ctrl(int keyCode) {
        return new KeyStroke(Modifiers.CTRL, keyCode);
    }

    public static KeyStroke alt(int keyCode) {
        return new KeyStroke(Modifiers.ALT, keyCode);
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

            case KeyCodes.KEY_LEFT:
                result = "Left arrow";

                break;

            case KeyCodes.KEY_RIGHT:
                result = "Right arrow";

                break;

            case KeyCodes.KEY_UP:
                result = "Up arrow";

                break;

            case KeyCodes.KEY_DOWN:
                result = "Down arrow";

                break;

            case KeyCodes.KEY_ENTER:
                result = "Enter";

                break;

            case KeyCodes.KEY_END:
                result = "End";

                break;

            case KeyCodes.KEY_HOME:
                result = "Home";

                break;

            case KeyCodes.KEY_PAGEDOWN:
                result = "Page Down";

                break;

            case KeyCodes.KEY_PAGEUP:
                result = "Page Up";

                break;

            case KeyCodes.KEY_ESCAPE:
                result = "Esc";

                break;

            case KeyCodes.KEY_SHIFT:
                result = "Shift";

                break;

            case KEY_SPACE:
                result = "Space";

                break;

            case KEY_PAUSE:
                result = "Pause";

                break;

            case 108:
                result = "Numpad -";

                break;

            case 107:
                result = "Numpad +";

                break;

            case KEY_FORWARD_SLASH:
                result = "/";
                break;

            case KeyCodes.KEY_CTRL:
                result = "Control";

                break;
            case KeyCodes.KEY_DELETE:
                result = "Delete";

                break;
            case KEY_INSERT:
                result = "Insert";

                break;
            default:
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
                + (modifiers.isCtrl() ? "CTRL-" : "")
                + getKeyString();
    }
}

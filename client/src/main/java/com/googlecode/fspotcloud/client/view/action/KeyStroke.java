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

package com.googlecode.fspotcloud.client.view.action;

import com.google.gwt.event.dom.client.KeyCodes;


public class KeyStroke {
    private final int keyCode;

    public KeyStroke(char keyCode) {
        this((int) keyCode);
    }

    public KeyStroke(int keyCode) {
        this.keyCode = (int) keyCode;
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

            case 32:
                result = "Space";

                break;

            case 19:
                result = "Pause";

                break;

            case 108:
                result = "Numpad -";

                break;

            case 107:
                result = "Numpad +";

                break;

            case KeyCodes.KEY_CTRL:
                result = "Control";

                break;

            default:
                result = String.valueOf((char) keyCode);

                break;
        }

        return result;
    }

    public String toString() {
        return "KeyStroke: " + getKeyString() + "(" + getKeyCode() + ")";
    }
}

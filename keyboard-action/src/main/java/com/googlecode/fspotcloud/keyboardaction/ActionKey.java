package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.base.Objects;
class ActionKey {
    private final String mode;
    private final KeyStroke keyStroke;

    public ActionKey(String mode, KeyStroke keyStroke) {
        this.keyStroke = keyStroke;
        this.mode = mode;
    }


    public String getMode() {
        return mode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActionKey) {
            ActionKey actionKey = (ActionKey) obj;
            return Objects.equal(mode, actionKey.mode) && Objects.equal(keyStroke, actionKey.keyStroke);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mode, keyStroke);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("keystroke", keyStroke).add("mode", mode).toString();
    }
}

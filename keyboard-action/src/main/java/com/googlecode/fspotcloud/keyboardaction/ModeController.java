package com.googlecode.fspotcloud.keyboardaction;


import java.util.logging.Level;
import java.util.logging.Logger;

class ModeController implements IModeController {

    private final Logger log = Logger.getLogger(ModeController.class.getName());
    private String mode;

    public ModeController(String mode) {
        setMode(mode);
    }

    @Override
    public void setMode(String mode) {
        this.mode = mode;
        log.log(Level.FINEST, "Set mode to: " + mode);
    }

    @Override
    public String getMode() {
        return mode;
    }
}

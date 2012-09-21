package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface IModeController {

    String getMode();

    void setMode(String mode);

    void initButtonEnableStates();
}

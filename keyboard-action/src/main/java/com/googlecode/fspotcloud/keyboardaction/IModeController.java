package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;

import java.util.Set;

@GwtCompatible
public interface IModeController {

    Set<String>  getFlags();

    void setFlag(String flag);

    void unsetFlag(String flag);

    void clearFlags();

     void initButtonEnableStates();
}

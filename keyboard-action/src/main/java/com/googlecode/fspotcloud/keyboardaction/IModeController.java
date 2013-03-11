package com.googlecode.fspotcloud.keyboardaction;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.place.shared.PlaceChangeEvent;

import java.util.Set;

@GwtCompatible
public interface IModeController extends PlaceChangeEvent.Handler {

    Set<String>  getFlags();

    void setFlag(String flag);

    void unsetFlag(String flag);

    void clearFlags();

     void initButtonEnableStates();
}

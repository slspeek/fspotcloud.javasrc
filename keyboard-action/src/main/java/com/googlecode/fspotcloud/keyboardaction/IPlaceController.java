package com.googlecode.fspotcloud.keyboardaction;

import com.google.gwt.place.shared.Place;

public interface IPlaceController {

    void goTo(Place newPlace);
    Place getWhere();
}

package com.googlecode.fspotcloud.client.main.ui;

import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButtonResources;

public interface BigActionButtonResources extends ActionButtonResources {

    @Source("bigactionbutton.css")
    BigButtonStyle style();

    interface BigButtonStyle extends Style { // inner-interface, just for fun
        // don't need to declare each String method
    }
}

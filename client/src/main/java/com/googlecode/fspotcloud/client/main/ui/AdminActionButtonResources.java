package com.googlecode.fspotcloud.client.main.ui;

import com.googlecode.fspotcloud.keyboardaction.gwt.ActionButtonResources;

public interface AdminActionButtonResources extends ActionButtonResources {

    @Source("adminactionbutton.css")
    AdminButtonStyle style();

    interface AdminButtonStyle extends Style { // inner-interface, just for fun
        // don't need to declare each String method
    }
}

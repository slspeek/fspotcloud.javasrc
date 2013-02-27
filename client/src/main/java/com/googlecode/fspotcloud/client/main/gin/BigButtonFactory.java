package com.googlecode.fspotcloud.client.main.gin;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.BigActionButtonResources;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;
import com.googlecode.fspotcloud.keyboardaction.ButtonFactory;

public class BigButtonFactory {

    private final ButtonFactory factory;

    @Inject
    public BigButtonFactory(ButtonFactory factory, BigActionButtonResources resources) {
        this.factory = factory;
        factory.setButtonResources(resources);

    }

    public ActionButton getButton(ActionUIDef actionUIDef) {
        return factory.getButton(actionUIDef);
    }
}

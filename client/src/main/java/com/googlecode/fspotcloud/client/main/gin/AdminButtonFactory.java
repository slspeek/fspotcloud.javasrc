package com.googlecode.fspotcloud.client.main.gin;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.AdminActionButtonResources;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.ButtonFactory;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;

public class AdminButtonFactory {

    private final ButtonFactory factory;

    @Inject
    public AdminButtonFactory(ButtonFactory factory, AdminActionButtonResources resources) {
        this.factory = factory;
        factory.setButtonResources(resources);

    }

    public ActionButton getButton(ActionUIDef actionUIDef) {
        return factory.getButton(actionUIDef);
    }
}

package com.googlecode.fspotcloud.client.main.gin;

import com.google.common.annotations.GwtCompatible;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.ui.AdminActionButtonResources;
import com.googlecode.fspotcloud.keyboardaction.ActionButton;
import com.googlecode.fspotcloud.keyboardaction.ActionUIDef;
import com.googlecode.fspotcloud.keyboardaction.WidgetFactory;

@GwtCompatible
public class AdminButtonFactory {

    private final WidgetFactory factory;

    @Inject
    public AdminButtonFactory(WidgetFactory factory, AdminActionButtonResources resources) {
        this.factory = factory;
        factory.setButtonResources(resources);
    }

    public ActionButton getButton(ActionUIDef actionUIDef) {
        return factory.getButton(actionUIDef);
    }
}

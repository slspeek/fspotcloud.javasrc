package com.googlecode.fspotcloud.client.useraction.dashboard;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.AbstractBinder;
import com.googlecode.fspotcloud.client.useraction.CategoryDef;
import com.googlecode.fspotcloud.client.useraction.Modes;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationActions;
import com.googlecode.fspotcloud.client.useraction.application.handler.*;
import com.googlecode.fspotcloud.keyboardaction.*;

public class DashboardBinder extends AbstractBinder {

    private final DashboardActions actions;


    @Inject
    public DashboardBinder(CategoryDef categoryDef,
                           DashboardActions actions) {
        super(categoryDef.DASHBOARD);
        this.actions = actions;
    }


    @Override
    public void build() {
        configBuilder.register(category, actions.reloadTree, get('R'));
    }


    private KeyboardBinding get(int characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.DASHBOARD);
    }

    private KeyboardBinding get(char characterCode) {
        return KeyboardBinding.bind(new KeyStroke(characterCode)).withDefaultModes(Modes.DASHBOARD);
    }


}

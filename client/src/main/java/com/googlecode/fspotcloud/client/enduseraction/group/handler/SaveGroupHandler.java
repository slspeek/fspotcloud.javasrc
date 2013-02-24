package com.googlecode.fspotcloud.client.enduseraction.group.handler;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.EditUserGroupView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;

public class SaveGroupHandler implements IActionHandler {


    @Inject
    EditUserGroupView.EditUserGroupPresenter presenter;

    @Override
    public void performAction(String actionId) {
        presenter.save();
    }
}

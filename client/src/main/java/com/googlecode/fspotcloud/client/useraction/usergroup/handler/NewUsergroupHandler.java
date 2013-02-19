package com.googlecode.fspotcloud.client.useraction.usergroup.handler;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.ManageGroups;
import com.googlecode.fspotcloud.client.main.view.api.ManageUserGroupsView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.NewUserGroupAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

@GwtCompatible
public class NewUsergroupHandler implements IActionHandler {

    private final DispatchAsync dispatch;
    private final ManageUserGroupsView.ManageUserGroupsPresenter presenter;
    private final StatusView statusView;

    @Inject
    public NewUsergroupHandler(DispatchAsync dispatch,
                               ManageUserGroupsView.ManageUserGroupsPresenter presenter,
                               @ManageGroups StatusView statusView) {
        this.dispatch = dispatch;
        this.presenter = presenter;
        this.statusView = statusView;
    }

    @Override
    public void performAction(String actionId) {
        statusView.setStatusText("Sending a request to create a new group");
        dispatch.execute(new NewUserGroupAction(),
                new AsyncCallback<GetUserGroupResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        statusView.setStatusText("Could not create a new group due to a server error");
                    }

                    @Override
                    public void onSuccess(GetUserGroupResult result) {
                        presenter.refreshData();
                    }
                });
    }
}

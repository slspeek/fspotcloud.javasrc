package com.googlecode.fspotcloud.client.enduseraction.group.handler;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.ManageGroups;
import com.googlecode.fspotcloud.client.main.view.api.ManageGroupsView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.NewUserGroupAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

@GwtCompatible
public class NewUsergroupHandler implements IActionHandler {

    private final DispatchAsync dispatch;
    private final ManageGroupsView.ManageGroupsPresenter presenter;
    private final StatusView statusView;

    @Inject
    public NewUsergroupHandler(DispatchAsync dispatch,
                               ManageGroupsView.ManageGroupsPresenter presenter,
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

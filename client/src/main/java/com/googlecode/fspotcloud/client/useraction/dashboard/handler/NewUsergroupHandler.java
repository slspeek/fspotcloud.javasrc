package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUserGroupsView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.NewUserGroupAction;
import net.customware.gwt.dispatch.client.DispatchAsync;

public class NewUsergroupHandler implements IActionHandler {

    private final DispatchAsync dispatch;
    private final ManageUserGroupsView.ManageUserGroupsPresenter presenter;

    @Inject
    public NewUsergroupHandler(DispatchAsync dispatch,
                               ManageUserGroupsView.ManageUserGroupsPresenter presenter) {
        this.dispatch = dispatch;
        this.presenter = presenter;
    }

    @Override
    public void performAction(String actionId) {
        dispatch.execute(new NewUserGroupAction(),
                new AsyncCallback<GetUserGroupResult>() {
                    @Override
                    public void onFailure(Throwable caught) {
                    }

                    @Override
                    public void onSuccess(GetUserGroupResult result) {
                        presenter.refreshData();
                    }
                });
    }
}

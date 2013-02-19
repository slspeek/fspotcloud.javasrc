package com.googlecode.fspotcloud.client.useraction.dashboard.handler;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUserGroupsView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.DeleteUserGroupAction;
import com.googlecode.fspotcloud.shared.main.GetUserGroupResult;
import com.googlecode.fspotcloud.shared.main.NewUserGroupAction;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

public class DeleteUsergroupHandler implements IActionHandler {

    private final DispatchAsync dispatch;
    private final ManageUserGroupsView.ManageUserGroupsPresenter presenter;

    @Inject
    public DeleteUsergroupHandler(DispatchAsync dispatch,
                                  ManageUserGroupsView.ManageUserGroupsPresenter presenter) {
        this.dispatch = dispatch;
        this.presenter = presenter;
    }

    @Override
    public void performAction(String actionId) {
        UserGroupInfo info = presenter.getSelected();

        if (info != null) {
            dispatch.execute(new DeleteUserGroupAction(info.getId()),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            //To change body of implemented methods use File | Settings | File Templates.
                        }

                        @Override
                        public void onSuccess(VoidResult result) {
                            presenter.refreshData();
                        }
                    });
        }
    }
}

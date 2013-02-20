package com.googlecode.fspotcloud.client.useraction.group.handler;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.ManageGroups;
import com.googlecode.fspotcloud.client.main.view.api.ManageGroupsView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.keyboardaction.IActionHandler;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.DeleteUserGroupAction;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

@GwtCompatible
public class DeleteUsergroupHandler implements IActionHandler {

    private final DispatchAsync dispatch;
    private final ManageGroupsView.ManageGroupsPresenter presenter;
    private final StatusView statusView;

    @Inject
    public DeleteUsergroupHandler(DispatchAsync dispatch,
                                  ManageGroupsView.ManageGroupsPresenter presenter,
                                  @ManageGroups StatusView statusView) {
        this.dispatch = dispatch;
        this.presenter = presenter;
        this.statusView = statusView;
    }

    @Override
    public void performAction(String actionId) {
        final UserGroupInfo info = presenter.getSelected();


        if (info != null) {
            statusView.setStatusText("Sending request to delete group " + info.getName() + " to the server");
            dispatch.execute(new DeleteUserGroupAction(info.getId()),
                    new AsyncCallback<VoidResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            statusView.setStatusText("Deleting group " + info.getName() + " failed due to a server error");
                        }

                        @Override
                        public void onSuccess(VoidResult result) {

                            presenter.refreshData();
                        }
                    });

        } else {
            statusView.setStatusText("Please make a selection before attempting a delete");
        }
    }
}

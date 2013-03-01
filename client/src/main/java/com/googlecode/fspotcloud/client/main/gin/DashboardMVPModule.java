package com.googlecode.fspotcloud.client.main.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;
import com.googlecode.fspotcloud.client.main.ui.*;
import com.googlecode.fspotcloud.client.main.view.*;
import com.googlecode.fspotcloud.client.main.view.api.*;

public class DashboardMVPModule extends AbstractGinModule {
    @Override
    protected void configure() {
        bind(StatusView.class).annotatedWith(Dashboard.class).to(StatusViewImpl.class).in(Singleton.class);
        bind(StatusView.class).annotatedWith(ManageGroups.class).to(StatusViewImpl.class).in(Singleton.class);
        bind(StatusView.class).annotatedWith(ManageUsers.class).to(StatusViewImpl.class).in(Singleton.class);
        bind(ManageGroupsView.class).to(ManageGroupsViewImpl.class).in(Singleton.class);
        bind(ManageGroupsView.ManageGroupsPresenter.class)
                .to(ManageGroupsActivity.class).in(Singleton.class);
        bind(EditUserGroupView.class).to(EditUserGroupViewImpl.class).in(Singleton.class);
        bind(EditUserGroupView.EditUserGroupPresenter.class)
                .to(EditUserGroupActivity.class).in(Singleton.class);
        bind(ManageUsersView.class).to(ManageUsersViewImpl.class).in(Singleton.class);
        bind(ManageUsersView.ManageUsersPresenter.class)
                .to(ManageUsersActivity.class).in(Singleton.class);
        bind(TagDetailsView.TagDetailsPresenter.class)
                .to(TagDetailsActivity.class).in(Singleton.class);
        bind(TagDetailsView.class).to(TagDetailsViewImpl.class)
                .in(Singleton.class);
        bind(DashboardView.class).to(DashboardViewImpl.class).in(Singleton.class);
        bind(DashboardView.DashboardPresenter.class)
                .to(DashboardActivity.class);
        bind(PeerActionsView.class).to(PeerActionsViewImpl.class)
                .in(Singleton.class);
        bind(PeerActionsView.PeerActionsPresenter.class).to(PeerActionsPresenter.class);
        bind(ITreeSelectionHandler.class).annotatedWith(AdminTreeView.class).to(AdminTreeSelectionHandler.class);
        bind(TreeView.class).annotatedWith(AdminTreeView.class).toProvider(AdminTreeViewProvider.class).in(Singleton.class);
        bind(TreeView.TreePresenter.class).annotatedWith(AdminTreeView.class).to(AdminTreePresenterImpl.class)
                .in(Singleton.class);
        bind(TagApprovalView.class).to(TagApprovalViewImpl.class).in(Singleton.class);
        bind(TagApprovalView.TagApprovalPresenter.class)
                .to(TagApprovalActivity.class).in(Singleton.class);

    }
}

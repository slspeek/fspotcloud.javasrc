package com.googlecode.fspotcloud.client.enduseraction.group.handler;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.gin.ManageGroups;
import com.googlecode.fspotcloud.client.main.view.api.ManageGroupsView;
import com.googlecode.fspotcloud.client.main.view.api.StatusView;
import com.googlecode.fspotcloud.client.place.ManageUsersPlace;
import com.googlecode.fspotcloud.client.place.api.IPlaceController;
import com.googlecode.fspotcloud.client.enduseraction.PlaceMoverBase;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

@GwtCompatible
public class GoManageUsers extends PlaceMoverBase {

    private final ManageGroupsView.ManageGroupsPresenter presenter;
    private final StatusView statusView;

    @Inject
    protected GoManageUsers(IPlaceController IPlaceController,
                            ManageGroupsView.ManageGroupsPresenter presenter,
                            @ManageGroups StatusView statusView)

    {
        super(IPlaceController);
        this.presenter = presenter;
        this.statusView = statusView;
    }

    @Override
    public Place getPlace() {
        UserGroupInfo info = presenter.getSelected();
        if (info != null) {
            return new ManageUsersPlace(info.getId());

        } else {
            statusView.setStatusText("Please selected a group first");
            return null;
        }
    }
}

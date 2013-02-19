package com.googlecode.fspotcloud.client.useraction.usergroup.handler;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageUserGroupsView;
import com.googlecode.fspotcloud.client.place.ManageUsersPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.useraction.PlaceMoverBase;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

@GwtCompatible
public class GoManageUsers extends PlaceMoverBase {

    private final ManageUserGroupsView.ManageUserGroupsPresenter presenter;

    @Inject
    protected GoManageUsers(PlaceGoTo placeGoTo,
                            ManageUserGroupsView.ManageUserGroupsPresenter presenter)

    {
        super(placeGoTo);
        this.presenter = presenter;
    }

    @Override
    public Place getPlace() {
        UserGroupInfo info = presenter.getSelected();
        if (info != null) {
            return new ManageUsersPlace(info.getId());

        } else {
            return null;
        }
    }
}

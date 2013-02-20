package com.googlecode.fspotcloud.client.useraction.group.handler;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.place.shared.Place;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.main.view.api.ManageGroupsView;
import com.googlecode.fspotcloud.client.place.EditUserGroupPlace;
import com.googlecode.fspotcloud.client.place.api.PlaceGoTo;
import com.googlecode.fspotcloud.client.useraction.PlaceMoverBase;
import com.googlecode.fspotcloud.shared.main.UserGroupInfo;

@GwtCompatible
public class GoEditUsergroup extends PlaceMoverBase {

    private final ManageGroupsView.ManageGroupsPresenter presenter;
    @Inject
    protected GoEditUsergroup(PlaceGoTo placeGoTo,
                              ManageGroupsView.ManageGroupsPresenter presenter)

    {
        super(placeGoTo);
        this.presenter = presenter;
    }

    @Override
    public Place getPlace() {
        UserGroupInfo info = presenter.getSelected();

        if (info != null) {
            return new EditUserGroupPlace(info.getId());
        }   else {
            return null;
        }
    }
}

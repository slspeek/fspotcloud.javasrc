package com.googlecode.fspotcloud.client.useraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.useraction.application.ApplicationLateBinder;
import com.googlecode.fspotcloud.client.useraction.dashboard.DashboardLateBinder;
import com.googlecode.fspotcloud.client.useraction.user.UserLateBinder;

public class UserActionLateBinder {


    @Inject
    UserActionLateBinder(ApplicationLateBinder applicationLateBinder,
                         DashboardLateBinder dashboardLateBinder,
                         UserLateBinder userLateBinder) {

    }

}

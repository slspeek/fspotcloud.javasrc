package com.googlecode.fspotcloud.client.enduseraction;

import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.enduseraction.application.ApplicationLateBinder;
import com.googlecode.fspotcloud.client.enduseraction.dashboard.DashboardLateBinder;
import com.googlecode.fspotcloud.client.enduseraction.group.GroupLateBinder;
import com.googlecode.fspotcloud.client.enduseraction.user.UserLateBinder;

public class UserActionLateBinder {


    @Inject
    UserActionLateBinder(ApplicationLateBinder applicationLateBinder,
                         DashboardLateBinder dashboardLateBinder,
                         UserLateBinder userLateBinder,
                         GroupLateBinder groupLateBinder) {

    }

}

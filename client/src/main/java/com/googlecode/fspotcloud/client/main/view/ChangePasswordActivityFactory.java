package com.googlecode.fspotcloud.client.main.view;

import com.googlecode.fspotcloud.client.main.view.api.ChangePasswordView;
import com.googlecode.fspotcloud.client.place.ChangePasswordPlace;

public interface ChangePasswordActivityFactory {

    ChangePasswordView.ChangePasswordPresenter get(ChangePasswordPlace place);
}

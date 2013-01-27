package com.googlecode.fspotcloud.client.main.view;

import com.googlecode.fspotcloud.client.main.view.api.MailFullsizeView;
import com.googlecode.fspotcloud.client.place.MailFullsizePlace;

public interface MailFullsizeActivityFactory {

    MailFullsizeView.MailFullsizePresenter get(MailFullsizePlace place);
}

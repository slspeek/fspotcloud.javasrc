package com.googlecode.fspotcloud.client.main;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.fspotcloud.shared.dashboard.VoidResult;
import com.googlecode.fspotcloud.shared.main.UserInfo;

public interface IClientLoginManager {

	void getUserInfoAsync(AsyncCallback<UserInfo> callback);

	void logout(AsyncCallback<VoidResult> resultAsyncCallback);

	void goTo3rdPartyLogin(String nextUrl);

	void redirectToLogin();

	void reset();

	void resetApplicationData();
}

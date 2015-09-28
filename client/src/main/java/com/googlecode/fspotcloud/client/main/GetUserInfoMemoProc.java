package com.googlecode.fspotcloud.client.main;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.client.data.MemoProcAsync;
import com.googlecode.fspotcloud.shared.main.GetUserInfo;
import com.googlecode.fspotcloud.shared.main.UserInfo;
import net.customware.gwt.dispatch.client.DispatchAsync;

public class GetUserInfoMemoProc extends MemoProcAsync<UserInfo> {

	final private DispatchAsync dispatchAsync;

	@Inject
	public GetUserInfoMemoProc(DispatchAsync dispatchAsync) {
		this.dispatchAsync = dispatchAsync;
	}

	@Override
	public void getAsyncImpl(AsyncCallback<UserInfo> callback) {
		dispatchAsync.execute(new GetUserInfo(""), callback);
	}
}

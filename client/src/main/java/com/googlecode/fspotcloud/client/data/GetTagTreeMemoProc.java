package com.googlecode.fspotcloud.client.data;

import com.google.common.annotations.GwtCompatible;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.googlecode.fspotcloud.shared.main.GetTagTreeAction;
import com.googlecode.fspotcloud.shared.main.TagTreeResult;
import net.customware.gwt.dispatch.client.DispatchAsync;

@GwtCompatible
public class GetTagTreeMemoProc extends MemoProcAsync<TagTreeResult> {

	final private DispatchAsync dispatchAsync;

	@Inject
	public GetTagTreeMemoProc(DispatchAsync dispatchAsync) {
		this.dispatchAsync = dispatchAsync;
	}

	public void getAsyncImpl(AsyncCallback<TagTreeResult> callback) {
		dispatchAsync.execute(new GetTagTreeAction(), callback);
	}
}

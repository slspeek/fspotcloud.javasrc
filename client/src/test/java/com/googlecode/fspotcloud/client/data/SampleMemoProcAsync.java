package com.googlecode.fspotcloud.client.data;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SampleMemoProcAsync extends MemoProcAsync<String> {

	private int callCount = 0;
	private AsyncCallback<String> callback;

	@Override
	public void getAsyncImpl(AsyncCallback<String> callback) {
		callCount++;
		this.callback = callback;

	}

	public int getCallCount() {
		return callCount;
	}

	public void setCallCOunt(int called) {

		this.callCount = called;
	}

	public void doCallback() {
		callback.onSuccess("Sample");
	}
}

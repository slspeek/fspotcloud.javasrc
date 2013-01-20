package com.googlecode.fspotcloud.client.data;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SampleCallback implements AsyncCallback<String> {
    private String result;

    public String getResult() {
        return result;
    }

    @Override
    public void onFailure(Throwable caught) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSuccess(String result) {
        this.result = result;
    }
}
